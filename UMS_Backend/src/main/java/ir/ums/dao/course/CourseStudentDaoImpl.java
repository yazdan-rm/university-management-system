package ir.ums.dao.course;

import ir.ums.builder.ViewQueryDao;
import ir.ums.dto.getRows.request.EnterpriseGetRowsRequest;
import ir.ums.dto.getRows.response.EnterpriseGetRowsResponse;
import ir.ums.model.course.CourseStudent;
import ir.ums.model.user.Student;
import ir.ums.repository.course.ICourseStudentRepository;
import ir.ums.service.user.IStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CourseStudentDaoImpl implements ICourseStudentDao {

    private final ViewQueryDao viewQueryDao;
    private final IStudentService studentService;
    private final ICourseStudentRepository courseStudentRepository;

    @Override
    public CourseStudent getCourseStudentById(UUID id) {
        return courseStudentRepository.findById(id).orElseThrow();
    }

    @Override
    public List<CourseStudent> getCoursesByStudentIdAndScoreResult(UUID id, Boolean scoreResult) {
        return courseStudentRepository.getCourseStudentByStudent_IdAndScoreResult(id, scoreResult);
    }

    @Override
    public void saveOrUpdateCourseStudent(CourseStudent courseStudent) {
        courseStudentRepository.save(courseStudent);
    }

    @Override
    public void deleteCourseStudentById(UUID uuid) {
        courseStudentRepository.deleteById(uuid);
    }

    @Override
    public EnterpriseGetRowsResponse getRowsCourseStudent(EnterpriseGetRowsRequest request) {
        Student currentStudent = studentService.getCurrentStudentLoggedIn();
        String viewQuery = """
                WITH course_student_view as (SELECT cs.id          AS "id",
                                                    cs.create_date AS "createDate",
                                                    c.course_name  AS "courseName",
                                                    c.course_units AS "courseUnits"
                                             FROM course c
                                                      INNER JOIN course_student cs ON cs.fk_course = c.id
                                             WHERE cs.fk_student = '::studentId')
                select *
                from course_student_view
                """;
        viewQuery = viewQuery.replace("::studentId", currentStudent.getId().toString());
        return viewQueryDao.getRows(request, viewQuery);
    }
}
