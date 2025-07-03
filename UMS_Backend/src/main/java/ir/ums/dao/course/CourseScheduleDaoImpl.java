package ir.ums.dao.course;

import ir.ums.builder.ViewQueryDao;
import ir.ums.dto.getRows.request.EnterpriseGetRowsRequest;
import ir.ums.dto.getRows.response.EnterpriseGetRowsResponse;
import ir.ums.mapper.course.CourseScheduleMapper;
import ir.ums.model.course.Course;
import ir.ums.model.course.CourseSchedule;
import ir.ums.repository.course.ICourseRepository;
import ir.ums.repository.course.ICourseScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CourseScheduleDaoImpl implements ICourseScheduleDao {

    private final ICourseScheduleRepository courseScheduleRepository;
    private final CourseScheduleMapper courseScheduleMapper;
    private final ICourseRepository courseRepository;
    private final ViewQueryDao viewQueryDao;

    @Override
    public CourseSchedule getCourseScheduleById(UUID id) {
        return courseScheduleRepository.findById(id).orElse(null);
    }

    @Override
    public void saveOrUpdateCourseSchedule(CourseSchedule courseSchedule) {
        courseScheduleRepository.save(courseSchedule);
    }

    @Override
    public void deleteCourseSchedule(UUID id) {
        courseScheduleRepository.deleteById(id);
    }

    @Override
    public EnterpriseGetRowsResponse getRowsCourseSchedule(EnterpriseGetRowsRequest request) {
        String viewQuery = """
                WITH course_schedule_view AS (SELECT id                AS "id",
                                                     create_date       AS "createDate",
                                                     update_date       AS "updateDate",
                                                     version           AS "version",
                                                     course_end_time   AS "courseEndTime",
                                                     course_exam_date  AS "courseExamDate",
                                                     course_exam_time  AS "courseExamTime",
                                                     course_start_time AS "courseStartTime",
                                                     days_of_week      AS "daysOfWeek"
                                              FROM course_schedule cs
                                              WHERE cs.fk_course = '::courseId')
                SELECT *
                FROM course_schedule_view
                """;
        viewQuery = viewQuery.replace("::courseId", request.getMasterId());
        return viewQueryDao.getRows(request, viewQuery);
    }

    public List<CourseSchedule> getCourseScheduleByCourse(Course course) {
        return courseScheduleRepository.findByCourse(course);
    }
}
