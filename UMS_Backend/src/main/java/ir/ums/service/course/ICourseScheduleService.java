package ir.ums.service.course;

import ir.ums.dto.course.CourseScheduleDTO;
import ir.ums.dto.getRows.request.EnterpriseGetRowsRequest;
import ir.ums.dto.getRows.response.EnterpriseGetRowsResponse;

import java.util.List;
import java.util.UUID;

public interface ICourseScheduleService {
    CourseScheduleDTO getCourseScheduleById(UUID id);
    List<CourseScheduleDTO> getCourseSchedulesByCourseId(UUID id);
    void saveCourseSchedule(CourseScheduleDTO courseScheduleDTO);
    void updateCourseSchedule(UUID courseScheduleId, CourseScheduleDTO courseScheduleDTO);
    void deleteCourseSchedule(UUID id);
    EnterpriseGetRowsResponse getRowsCourseSchedule(EnterpriseGetRowsRequest request);
}
