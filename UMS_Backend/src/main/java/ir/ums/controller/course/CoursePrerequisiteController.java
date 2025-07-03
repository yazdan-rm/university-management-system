package ir.ums.controller.course;

import ir.ums.constants.ApiPaths;
import ir.ums.constants.Messages;
import ir.ums.dto.ApiResponse;
import ir.ums.dto.course.CoursePrerequisiteDTO;
import ir.ums.dto.getRows.request.EnterpriseGetRowsRequest;
import ir.ums.dto.getRows.response.EnterpriseGetRowsResponse;
import ir.ums.service.course.ICoursePrerequisiteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPaths.CoursePrerequisite.BASE)
public class CoursePrerequisiteController {

    private final ICoursePrerequisiteService coursePrerequisiteService;

    @PreAuthorize("hasAnyRole('admin', 'student')")
    @GetMapping(value = ApiPaths.CoursePrerequisite.BY_COURSE_ID)
    public ResponseEntity<ApiResponse<List<CoursePrerequisiteDTO>>> getCoursePrerequisiteByCourseId(@PathVariable UUID courseId) {
        return new ResponseEntity<>(ApiResponse.<List<CoursePrerequisiteDTO>>builder()
                .data(coursePrerequisiteService.getCoursePrerequisiteByCourseId(courseId))
                .message(Messages.DATA_WAS_FOUND)
                .build(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('admin', 'student')")
    @GetMapping(value = ApiPaths.CoursePrerequisite.BY_ID)
    public ResponseEntity<ApiResponse<CoursePrerequisiteDTO>> getCoursePrerequisiteId(@PathVariable UUID id) {
        return new ResponseEntity<>(ApiResponse.<CoursePrerequisiteDTO>builder()
                .data(coursePrerequisiteService.getCoursePrerequisiteById(id))
                .message(Messages.DATA_WAS_FOUND)
                .build(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('admin')")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> saveCoursePrerequisite(@RequestBody @Valid CoursePrerequisiteDTO coursePrerequisiteDTO) {
        coursePrerequisiteService.saveCoursePrerequisite(coursePrerequisiteDTO);
        return new ResponseEntity<>(ApiResponse.<Void>builder()
                .message(Messages.DATA_SAVED_SUCCESSFULLY)
                .build(), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('admin')")
    @PutMapping(value = ApiPaths.CoursePrerequisite.BY_ID)
    public ResponseEntity<ApiResponse<Void>> updateCoursePrerequisite(@PathVariable UUID id, @RequestBody @Valid CoursePrerequisiteDTO coursePrerequisiteDTO) {
        coursePrerequisiteService.updateCoursePrerequisite(id, coursePrerequisiteDTO);
        return new ResponseEntity<>(ApiResponse.<Void>builder()
                .message(Messages.DATA_UPDATED_SUCCESSFULLY)
                .build(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('admin')")
    @DeleteMapping(value = ApiPaths.CoursePrerequisite.BY_ID)
    public ResponseEntity<ApiResponse<Void>> deleteCoursePrerequisite(@PathVariable UUID id) {
        coursePrerequisiteService.deleteCoursePrerequisite(id);
        return new ResponseEntity<>(ApiResponse.<Void>builder()
                .message(Messages.DATA_HAS_BEEN_DELETED)
                .build(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('admin', 'student')")
    @PostMapping(ApiPaths.CoursePrerequisite.GET_ROWS)
    public ResponseEntity<ApiResponse<EnterpriseGetRowsResponse>> getRowsCoursePrerequisites(@RequestBody @Valid EnterpriseGetRowsRequest request) {
        return new ResponseEntity<>(ApiResponse.<EnterpriseGetRowsResponse>builder()
                .data(coursePrerequisiteService.getRowsCoursePrerequisites(request))
                .message(Messages.DATA_WAS_FOUND)
                .build(), HttpStatus.OK);
    }
}
