package ir.ums.controller.user;

import ir.ums.constants.ApiPaths;
import ir.ums.constants.Messages;
import ir.ums.dto.ApiResponse;
import ir.ums.dto.user.StudentDTO;
import ir.ums.model.user.Student;
import ir.ums.service.user.IStudentService;
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
@RequestMapping(ApiPaths.Student.BASE)
public class StudentController {

    private final IStudentService studentService;

    @PreAuthorize("hasAnyRole('admin', 'student')")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> saveStudent(@RequestBody @Valid StudentDTO studentDTO) {
        studentService.saveStudent(studentDTO);
        return new ResponseEntity<>(ApiResponse.<Void>builder()
                .message(Messages.DATA_SAVED_SUCCESSFULLY)
                .build(), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('admin', 'student')")
    @PutMapping(ApiPaths.Student.BY_USER_ID)
    public ResponseEntity<ApiResponse<Student>> updateStudent(@PathVariable UUID userId, @RequestBody @Valid StudentDTO studentDTO) {
        return new ResponseEntity<>(ApiResponse.<Student>builder()
                .data(studentService.updateStudent(userId, studentDTO))
                .message(Messages.DATA_UPDATED_SUCCESSFULLY)
                .build(), HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('admin', 'student')")
    @GetMapping(ApiPaths.Student.BY_USER_ID)
    public ResponseEntity<ApiResponse<StudentDTO>> getStudent(@PathVariable UUID userId) {
        return new ResponseEntity<>(ApiResponse.<StudentDTO>builder()
                .data(studentService.getStudentByKeycloakUserId(userId))
                .message(Messages.DATA_WAS_FOUND)
                .build(), HttpStatus.OK);
    }
}
