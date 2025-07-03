package ir.ums.controller.course;

import ir.ums.constants.ApiPaths;
import ir.ums.constants.Messages;
import ir.ums.dto.ApiResponse;
import ir.ums.dto.course.CourseStudentDTO;
import ir.ums.dto.getRows.request.EnterpriseGetRowsRequest;
import ir.ums.dto.getRows.response.EnterpriseGetRowsResponse;
import ir.ums.service.course.ICourseStudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPaths.CourseStudent.BASE)
public class CourseStudentController {

    private final ICourseStudentService courseStudentService;

    @PreAuthorize("hasRole('student')")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> saveCourseStudent(@RequestBody @Valid CourseStudentDTO courseStudentDTO) {
        courseStudentService.saveCourseStudent(courseStudentDTO);
        return new ResponseEntity<>(ApiResponse.<Void>builder()
                .message(Messages.DATA_SAVED_SUCCESSFULLY)
                .build(), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('student')")
    @PutMapping(ApiPaths.CourseStudent.BY_ID)
    public ResponseEntity<ApiResponse<Void>> updateCourseStudent(@PathVariable UUID id, @RequestBody @Valid CourseStudentDTO courseStudentDTO) {
        courseStudentService.updateCourseStudent(id, courseStudentDTO);
        return new ResponseEntity<>(ApiResponse.<Void>builder()
                .message(Messages.DATA_UPDATED_SUCCESSFULLY)
                .build(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('student')")
    @DeleteMapping(ApiPaths.CourseStudent.BY_ID)
    public ResponseEntity<ApiResponse<Void>> deleteCourseStudentById(@PathVariable UUID id) {
        courseStudentService.deleteCourseStudentById(id);
        return new ResponseEntity<>(ApiResponse.<Void>builder()
                .message(Messages.DATA_HAS_BEEN_DELETED)
                .build(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('admin', 'student')")
    @PostMapping(ApiPaths.CourseStudent.GET_ROWS)
    public ResponseEntity<ApiResponse<EnterpriseGetRowsResponse>> getRows(@RequestBody @Valid EnterpriseGetRowsRequest request) {
        return new ResponseEntity<>(ApiResponse.<EnterpriseGetRowsResponse>builder()
                .data(courseStudentService.getRowsCourseStudent(request))
                .message(Messages.DATA_WAS_FOUND)
                .build(), HttpStatus.OK);
    }
}
