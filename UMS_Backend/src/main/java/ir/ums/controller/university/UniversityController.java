package ir.ums.controller.university;

import ir.ums.constants.ApiPaths;
import ir.ums.constants.Messages;
import ir.ums.dto.ApiResponse;
import ir.ums.dto.university.UniversityTitleCodeDTO;
import ir.ums.service.university.IUniversityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPaths.University.BASE)
public class UniversityController {

    private final IUniversityService universityService;

    @PreAuthorize("hasAnyRole('admin', 'student')")
    @GetMapping(ApiPaths.University.GET_ALL_COLLEGES)
    public ResponseEntity<ApiResponse<List<UniversityTitleCodeDTO>>> getAllColleges() {
        return new ResponseEntity<>(ApiResponse.<List<UniversityTitleCodeDTO>>builder()
                .data(universityService.getAllColleges())
                .message(Messages.DATA_WAS_FOUND)
                .build(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('admin', 'student')")
    @GetMapping(ApiPaths.University.GET_DEPARTMENTS_BY_COLLEGE_CODE)
    public ResponseEntity<ApiResponse<List<UniversityTitleCodeDTO>>> getDepartmentsByCollegeCode(@PathVariable Long collegeCode) {
        return new ResponseEntity<>(ApiResponse.<List<UniversityTitleCodeDTO>>builder()
                .data(universityService.getDepartmentsByCollegeCode(collegeCode))
                .message(Messages.DATA_WAS_FOUND)
                .build(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('admin', 'student')")
    @GetMapping(ApiPaths.University.GET_FIELD_OF_STUDY_BY_DEPARTMENT_CODE)
    public ResponseEntity<ApiResponse<List<UniversityTitleCodeDTO>>> getFieldOfStudyByDepartmentCode(@PathVariable Long departmentCode) {
        return new ResponseEntity<>(ApiResponse.<List<UniversityTitleCodeDTO>>builder()
                .data(universityService.getFieldOfStudyByDepartmentCode(departmentCode))
                .message(Messages.DATA_WAS_FOUND)
                .build(), HttpStatus.OK);
    }
}
