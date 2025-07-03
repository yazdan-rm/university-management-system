package ir.ums.controller.course;

import ir.ums.constants.ApiPaths;
import ir.ums.constants.Messages;
import ir.ums.dto.ApiResponse;
import ir.ums.dto.course.CourseScheduleDTO;
import ir.ums.dto.getRows.request.EnterpriseGetRowsRequest;
import ir.ums.dto.getRows.response.EnterpriseGetRowsResponse;
import ir.ums.service.course.ICourseScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping(ApiPaths.CourseSchedule.BASE)
public class CourseScheduleController {

    private final ICourseScheduleService courseScheduleService;

    @PreAuthorize("hasAnyRole('student', 'admin')")
    @GetMapping(value = ApiPaths.CourseSchedule.BY_ID)
    public ResponseEntity<ApiResponse<CourseScheduleDTO>> getCourseScheduleById(@PathVariable UUID id) {
        return new ResponseEntity<>(ApiResponse.<CourseScheduleDTO>builder()
                .data(courseScheduleService.getCourseScheduleById(id))
                .message(Messages.DATA_WAS_FOUND)
                .build(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('admin')")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> saveCourseSchedule(@RequestBody @Valid CourseScheduleDTO courseScheduleDTO) {
        courseScheduleService.saveCourseSchedule(courseScheduleDTO);
        return new ResponseEntity<>(ApiResponse.<Void>builder()
                .message(Messages.DATA_SAVED_SUCCESSFULLY)
                .build(), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('admin')")
    @PutMapping(ApiPaths.CourseSchedule.BY_ID)
    public ResponseEntity<ApiResponse<Void>> updateCourse(@PathVariable UUID id, @RequestBody @Valid CourseScheduleDTO courseScheduleDTO) {
        courseScheduleService.updateCourseSchedule(id, courseScheduleDTO);
        return new ResponseEntity<>(ApiResponse.<Void>builder()
                .message(Messages.DATA_UPDATED_SUCCESSFULLY)
                .build(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('admin')")
    @DeleteMapping(value = ApiPaths.CourseSchedule.BY_ID)
    public ResponseEntity<ApiResponse<Void>> deleteCourseScheduleById(@PathVariable UUID id) {
        courseScheduleService.deleteCourseSchedule(id);
        return new ResponseEntity<>(ApiResponse.<Void>builder()
                .message(Messages.DATA_HAS_BEEN_DELETED)
                .build(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('admin', 'student')")
    @PostMapping(value = ApiPaths.CourseSchedule.GET_ROWS)
    public ResponseEntity<ApiResponse<EnterpriseGetRowsResponse>> getRowsCourseSchedules(@RequestBody @Valid EnterpriseGetRowsRequest request) {
        return new ResponseEntity<>(ApiResponse.<EnterpriseGetRowsResponse>builder()
                .data(courseScheduleService.getRowsCourseSchedule(request))
                .message(Messages.DATA_WAS_FOUND)
                .build(), HttpStatus.OK);
    }
}
