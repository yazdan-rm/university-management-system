package ir.ums.service.course;

import ir.ums.dto.course.CourseDTO;
import ir.ums.dto.course.CourseTitleIdDTO;
import ir.ums.dto.getRows.request.EnterpriseGetRowsRequest;
import ir.ums.dto.getRows.response.EnterpriseGetRowsResponse;

import java.util.List;
import java.util.UUID;

public interface ICourseService {
    CourseDTO getCourseById(UUID id);
    void saveCourse(CourseDTO courseDTO);
    void updateCourse(UUID courseId, CourseDTO courseDTO);
    void deleteCourse(UUID id);
    List<CourseTitleIdDTO> getAllCourses(UUID courseId);
    EnterpriseGetRowsResponse getRowsCourses(EnterpriseGetRowsRequest request);
    EnterpriseGetRowsResponse getExclusiveCoursesForStudent(EnterpriseGetRowsRequest request);
    EnterpriseGetRowsResponse getStudentEnrollmentResult(EnterpriseGetRowsRequest request);
}
