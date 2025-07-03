package ir.ums.dao.course;

import ir.ums.dto.getRows.request.EnterpriseGetRowsRequest;
import ir.ums.dto.getRows.response.EnterpriseGetRowsResponse;
import ir.ums.model.course.CourseStudent;

import java.util.List;
import java.util.UUID;

public interface ICourseStudentDao {
    CourseStudent getCourseStudentById(UUID id);
    List<CourseStudent> getCoursesByStudentIdAndScoreResult(UUID id, Boolean scoreResult);
    void saveOrUpdateCourseStudent(CourseStudent courseStudent);
    void deleteCourseStudentById(UUID uuid);
    EnterpriseGetRowsResponse getRowsCourseStudent(EnterpriseGetRowsRequest request);
}
