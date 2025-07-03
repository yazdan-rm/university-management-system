package ir.ums.service.university;

import ir.ums.dto.university.UniversityTitleCodeDTO;

import java.util.List;

public interface IUniversityService {
    List<UniversityTitleCodeDTO> getAllColleges();
    List<UniversityTitleCodeDTO> getDepartmentsByCollegeCode(Long collegeCode);
    List<UniversityTitleCodeDTO> getFieldOfStudyByDepartmentCode(Long departmentCode);
}
