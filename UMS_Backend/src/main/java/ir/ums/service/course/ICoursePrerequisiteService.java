package ir.ums.service.course;

import ir.ums.dto.course.CoursePrerequisiteDTO;
import ir.ums.dto.getRows.request.EnterpriseGetRowsRequest;
import ir.ums.dto.getRows.response.EnterpriseGetRowsResponse;

import java.util.List;
import java.util.UUID;

public interface ICoursePrerequisiteService {
    CoursePrerequisiteDTO getCoursePrerequisiteById(UUID id);
    List<CoursePrerequisiteDTO> getCoursePrerequisiteByCourseId(UUID courseId);
    List<CoursePrerequisiteDTO> getCoursePrerequisiteByPrerequisiteId(UUID prerequisiteId);
    void saveCoursePrerequisite(CoursePrerequisiteDTO dto);
    void updateCoursePrerequisite(UUID id, CoursePrerequisiteDTO dto);
    void deleteCoursePrerequisite(UUID id);
    EnterpriseGetRowsResponse getRowsCoursePrerequisites(EnterpriseGetRowsRequest request);
}
