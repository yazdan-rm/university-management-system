package ir.ums.dao.course;

import ir.ums.dto.course.CoursePrerequisiteDTO;
import ir.ums.dto.getRows.request.EnterpriseGetRowsRequest;
import ir.ums.dto.getRows.response.EnterpriseGetRowsResponse;
import ir.ums.model.course.CoursePrerequisite;

import java.util.List;
import java.util.UUID;

public interface ICoursePrerequisiteDao {
    CoursePrerequisite getCoursePrerequisiteById(UUID id);
    List<CoursePrerequisite> getCoursePrerequisiteByCourseId(UUID courseId);
    List<CoursePrerequisite> getCoursePrerequisiteByCourseIdAndPreType(UUID courseId, String preType);
    List<CoursePrerequisite> getCoursePrerequisiteByPrerequisiteId(UUID prerequisiteId);
    void saveOrUpdateCoursePrerequisite(CoursePrerequisite coursePrerequisite);
    void deleteCoursePrerequisite(UUID id);
    EnterpriseGetRowsResponse getRowsCoursePrerequisite(EnterpriseGetRowsRequest request);
}
