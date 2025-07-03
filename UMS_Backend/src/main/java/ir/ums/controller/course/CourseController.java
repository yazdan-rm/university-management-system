package ir.ums.controller.course;

import ir.ums.constants.ApiPaths;
import ir.ums.constants.Messages;
import ir.ums.dto.ApiResponse;
import ir.ums.dto.course.CourseDTO;
import ir.ums.dto.course.CourseTitleIdDTO;
import ir.ums.dto.getRows.request.EnterpriseGetRowsRequest;
import ir.ums.dto.getRows.response.EnterpriseGetRowsResponse;
import ir.ums.service.course.ICourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPaths.Course.BASE)
public class CourseController {

    private final ICourseService courseService;

    @PreAuthorize("hasAnyRole('admin', 'student')")
    @GetMapping(value = ApiPaths.Course.BY_ID)
    public ResponseEntity<ApiResponse<CourseDTO>> getCourseById(@PathVariable UUID id) {
        return new ResponseEntity<>(ApiResponse.<CourseDTO>builder()
                .data(courseService.getCourseById(id))
                .message(Messages.DATA_WAS_FOUND)
                .build(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('admin')")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> saveCourse(@RequestBody @Valid CourseDTO courseDTO) {
        courseService.saveCourse(courseDTO);
        return new ResponseEntity<>(ApiResponse.<Void>builder()
                .message(Messages.DATA_SAVED_SUCCESSFULLY)
                .build(), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('admin')")
    @PutMapping(ApiPaths.Course.BY_ID)
    public ResponseEntity<ApiResponse<Void>> updateCourse(@PathVariable UUID id, @RequestBody @Valid CourseDTO courseDTO) {
        courseService.updateCourse(id, courseDTO);
        return new ResponseEntity<>(ApiResponse.<Void>builder()
                .message(Messages.DATA_UPDATED_SUCCESSFULLY)
                .build(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('admin')")
    @DeleteMapping(value = ApiPaths.Course.BY_ID)
    public ResponseEntity<ApiResponse<Void>> deleteCourseById(@PathVariable UUID id) {
        courseService.deleteCourse(id);
        return new ResponseEntity<>(ApiResponse.<Void>builder()
                .message(Messages.DATA_HAS_BEEN_DELETED)
                .build(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('admin', 'student')")
    @GetMapping(value = ApiPaths.Course.GET_ALL)
    public ResponseEntity<ApiResponse<List<CourseTitleIdDTO>>> getAllCourses(@PathVariable UUID courseId) {
        return new ResponseEntity<>(ApiResponse.<List<CourseTitleIdDTO>>builder()
                .data(courseService.getAllCourses(courseId))
                .message(Messages.DATA_WAS_FOUND)
                .build(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('admin', 'student')")
    @PostMapping(ApiPaths.Course.GET_ROWS)
    public ResponseEntity<ApiResponse<EnterpriseGetRowsResponse>> getRowsCourses(@RequestBody @Valid EnterpriseGetRowsRequest request) {
        return new ResponseEntity<>(ApiResponse.<EnterpriseGetRowsResponse>builder()
                .data(courseService.getRowsCourses(request))
                .message(Messages.DATA_WAS_FOUND)
                .build(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('student')")
    @PostMapping(ApiPaths.Course.GET_EXCLUSIVE_COURSES_FOR_STUDENT)
    public ResponseEntity<ApiResponse<EnterpriseGetRowsResponse>> getRows(@RequestBody @Valid EnterpriseGetRowsRequest request) {
        return new ResponseEntity<>(ApiResponse.<EnterpriseGetRowsResponse>builder()
                .data(courseService.getExclusiveCoursesForStudent(request))
                .message(Messages.DATA_WAS_FOUND)
                .build(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('student')")
    @PostMapping(ApiPaths.Course.GET_STUDENT_ENROLLMENT_RESULT)
    public ResponseEntity<ApiResponse<EnterpriseGetRowsResponse>> getStudentEnrollmentResult(@RequestBody @Valid EnterpriseGetRowsRequest request) {
        return new ResponseEntity<>(ApiResponse.<EnterpriseGetRowsResponse>builder()
                .data(courseService.getStudentEnrollmentResult(request))
                .message(Messages.DATA_WAS_FOUND)
                .build(), HttpStatus.OK);
    }
}
