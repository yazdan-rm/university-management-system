package ir.ums.dao.course;

import ir.ums.dto.getRows.request.EnterpriseGetRowsRequest;
import ir.ums.dto.getRows.response.EnterpriseGetRowsResponse;
import ir.ums.model.course.Course;
import ir.ums.model.course.CourseSchedule;

import java.util.List;
import java.util.UUID;

public interface ICourseScheduleDao {
    CourseSchedule getCourseScheduleById(UUID id);
    void saveOrUpdateCourseSchedule(CourseSchedule courseSchedule);
    void deleteCourseSchedule(UUID id);
    EnterpriseGetRowsResponse getRowsCourseSchedule(EnterpriseGetRowsRequest request);
    List<CourseSchedule> getCourseScheduleByCourse(Course course);
}
