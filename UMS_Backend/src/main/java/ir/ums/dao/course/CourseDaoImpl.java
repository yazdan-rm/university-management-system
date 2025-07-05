package ir.ums.dao.course;

import ir.ums.builder.ViewQueryDao;
import ir.ums.dto.course.CourseTitleIdDTO;
import ir.ums.dto.getRows.request.EnterpriseGetRowsRequest;
import ir.ums.dto.getRows.response.EnterpriseGetRowsResponse;
import ir.ums.model.course.Course;
import ir.ums.model.user.Student;
import ir.ums.repository.course.ICourseRepository;
import ir.ums.service.user.IStudentService;
import ir.ums.utils.DateConverterUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CourseDaoImpl implements ICourseDao {

    private final IStudentService studentService;
    private final ICourseRepository courseRepository;
    private final ViewQueryDao viewQueryDao;

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Course getCourseById(UUID id) {
        return courseRepository.findById(id).orElseThrow();
    }

    @Override
    public void saveOrUpdateCourse(Course course) {
        courseRepository.save(course);
    }

    @Override
    public void deleteCourse(UUID id) {
        courseRepository.deleteById(id);
    }

    @Override
    public List<CourseTitleIdDTO> getAllCourses() {
        return entityManager.createQuery("""
                select new ir.ums.dto.course.CourseTitleIdDTO(c.id, c.courseName)
                from Course c
                order by c.createDate desc
                """, CourseTitleIdDTO.class).getResultList();
    }

    @Override
    public EnterpriseGetRowsResponse getRowsCourses(EnterpriseGetRowsRequest request) {
        String viewQuery = """
                WITH courses_view AS (SELECT c.id                   AS "id",
                                        c.create_date               AS "createDate",
                                        c.course_name               AS "courseName",
                                        c.semester                  AS "semester",
                                        c.course_units              AS "courseUnits",
                                        c.pv_allowed_genders        AS "allowedGenders",
                                        c.instructor_name           AS "instructorName",
                                        c.capacity                  AS "capacity",
                                        c.enrolled_count            AS "enrolledCount",
                                        c.educational_level         AS "educationalLevel",
                                        c.location                  AS "location",
                                        c.has_prerequisite_course   AS "hasPrerequisiteCourse",
                                        u.college_name              AS "collegeName",
                                        u.department_name           AS "departmentName",
                                        u.field_of_study            AS "fieldOfStudy",
                                        u.college_code              AS "collegeCode",
                                        u.department_code           AS "departmentCode",
                                        u.field_of_study_code       AS "fieldOfStudyCode"
                                 FROM course c
                                          JOIN
                                      university u
                                      ON u.id = c.fk_university)
                SELECT *
                FROM courses_view
                """;
        return viewQueryDao.getRows(request, viewQuery);
    }

    @Override
    public EnterpriseGetRowsResponse getExclusiveCoursesForStudent(EnterpriseGetRowsRequest request) {
        Student currentStudent = studentService.getCurrentStudentLoggedIn();

        String sql = """
                WITH exc_crs_for_std_view AS (
                SELECT MIN(c.id)                                       AS "courseId",
                       MIN(c.create_date)                              AS "createDate",
                       MIN(c.course_name)                              AS "courseName",
                       MIN(c.course_units)                             AS "courseUnits",
                       MIN(c.capacity)                                 AS "capacity",
                       MIN(c.pv_allowed_genders)                       AS "allowedGenders",
                       MIN(c.instructor_name)                          AS "instructorName",
                       MIN(c.enrolled_count)                           AS "enrolledCount",
                       MIN(c.has_prerequisite_course)                  AS "hasPrerequisiteCourse",
                       LISTAGG(DISTINCT csched.days_of_week || ' ' || csched.course_end_time || '-' || csched.course_start_time, ' / ') AS "scheduleTime",
                       LISTAGG(DISTINCT 'امتحان ' || csched.course_exam_date || ' ساعت ' || csched.course_exam_time, ' / ')             AS "examTime",
                       LISTAGG(DISTINCT cp.prerequisite_type || ' - ' || crs.course_name, ' / ')                                        AS "prerequisiteCourse"
                    FROM course c
                             INNER JOIN university u ON u.id = c.fk_university
                             LEFT JOIN course_schedule csched ON c.id = csched.fk_course
                             LEFT JOIN course_prerequisite cp ON c.id = cp.fk_course_id
                             LEFT JOIN course crs ON crs.id = cp.fk_prerequisite_id
                    WHERE c.semester = ::currentSemester
                      AND c.educational_level = ::stdEducationalLevel
                      AND u.id = '::stdFkUniversity'
                      AND NOT EXISTS (
                            SELECT 1
                            FROM course_student cs
                            WHERE cs.fk_course = c.id
                              AND cs.fk_student = '::studentId'
                              AND (cs.score_result = 0 OR cs.score_result IS NULL)
                      )
                    GROUP BY c.id
                )
                SELECT *
                FROM exc_crs_for_std_view
                """;
        sql = sql.replace("::currentSemester", getCurrentSemester()).replace("::stdEducationalLevel", currentStudent.getEducationalLevel().toString()).replace("::stdFkUniversity", currentStudent.getUniversity().getId().toString()).replace("::studentId", currentStudent.getId().toString());
        return viewQueryDao.getRows(request, sql);
    }

    private String getCurrentSemester() {
        String currentShamsiDate = DateConverterUtil.dateToShamsi(LocalDate.now());
        int currentYear = Integer.parseInt(currentShamsiDate.split("/")[0]) - 1000;
        int currentMonth = Integer.parseInt(currentShamsiDate.split("/")[1]);
        if ((currentMonth >= 7 && currentMonth < 11) || (currentMonth >= 1 && currentMonth <= 3))
            return currentYear + "1";
        else if (currentMonth == 11 || currentMonth == 12) return currentYear + "2";
        else if (currentMonth >= 4 && currentMonth < 7) return currentYear + "3";
        return "-1";
    }

    public EnterpriseGetRowsResponse getStudentEnrollmentResult(EnterpriseGetRowsRequest request) {
        Student currentStudent = studentService.getCurrentStudentLoggedIn();
        String queryView = """
                WITH student_enrollment_result_view AS (
                                SELECT MIN(c.course_name)     AS "courseName",
                                       MIN(c.course_units)    AS "courseUnits",
                                       MIN(c.instructor_name) AS "instructorName",
                                       LISTAGG(CASE WHEN csch.days_of_week = 'شنبه' THEN csch.course_end_time || '-' || csch.course_start_time END, ' / ') AS "saturday",
                                       LISTAGG(CASE WHEN csch.days_of_week = 'یک‌شنبه' THEN csch.course_end_time || '-' || csch.course_start_time END, ' / ') AS "sunday",
                                       LISTAGG(CASE WHEN csch.days_of_week = 'دوشنبه' THEN csch.course_end_time || '-' || csch.course_start_time END, ' / ') AS "monday",
                                       LISTAGG(CASE WHEN csch.days_of_week = 'سه‌شنبه' THEN csch.course_end_time || '-' || csch.course_start_time END, ' / ') AS "tuesday",
                                       LISTAGG(CASE WHEN csch.days_of_week = 'چهارشنبه' THEN csch.course_end_time || '-' || csch.course_start_time END, ' / ') AS "wednesday",
                                       LISTAGG(CASE WHEN csch.days_of_week = 'پنج‌شنبه' THEN csch.course_end_time || '-' || csch.course_start_time END, ' / ') AS "thursday",
                                       MIN(csch.course_exam_date || ' ساعت ' || csch.course_exam_time) AS "examTime"
                                FROM course_student cs
                                INNER JOIN course c ON cs.fk_course = c.id
                                LEFT JOIN course_schedule csch ON csch.fk_course = c.id
                                WHERE cs.fk_student = '::studentId'
                                GROUP BY c.id)
                SELECT *
                FROM student_enrollment_result_view
                """;
        queryView = queryView.replace("::studentId", currentStudent.getId().toString());
        return viewQueryDao.getRows(request, queryView);
    }
}