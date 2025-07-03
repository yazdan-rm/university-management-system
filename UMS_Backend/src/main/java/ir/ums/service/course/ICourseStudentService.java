package ir.ums.service.course;

import ir.ums.dto.course.CourseStudentDTO;
import ir.ums.dto.getRows.request.EnterpriseGetRowsRequest;
import ir.ums.dto.getRows.response.EnterpriseGetRowsResponse;

import java.util.UUID;

public interface ICourseStudentService {
    void saveCourseStudent(CourseStudentDTO courseStudentDTO);
    void updateCourseStudent(UUID uuid, CourseStudentDTO courseStudentDTO);
    void deleteCourseStudentById(UUID uuid);
    EnterpriseGetRowsResponse getRowsCourseStudent(EnterpriseGetRowsRequest request);
}
