package ir.ums.dao.course;

import ir.ums.dto.course.CourseTitleIdDTO;
import ir.ums.dto.getRows.request.EnterpriseGetRowsRequest;
import ir.ums.dto.getRows.response.EnterpriseGetRowsResponse;
import ir.ums.model.course.Course;

import java.util.List;
import java.util.UUID;

public interface ICourseDao {
    Course getCourseById(UUID id);
    void saveOrUpdateCourse(Course course);
    void deleteCourse(UUID id);
    List<CourseTitleIdDTO> getAllCourses();
    EnterpriseGetRowsResponse getRowsCourses(EnterpriseGetRowsRequest request);
    EnterpriseGetRowsResponse getExclusiveCoursesForStudent(EnterpriseGetRowsRequest request);
    EnterpriseGetRowsResponse getStudentEnrollmentResult(EnterpriseGetRowsRequest request);
}
