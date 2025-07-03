package ir.ums.dao.university;

import ir.ums.dto.university.UniversityTitleCodeDTO;
import ir.ums.model.university.University;

import java.util.List;

public interface IUniversityDao {

    University getUniversityByCodes(Long collegeCode, Long departmentCode, Long fieldOfStudyCode);
    List<UniversityTitleCodeDTO> getAllColleges();
    List<UniversityTitleCodeDTO> getDepartmentsByCollegeCode(Long collegeCode);
    List<UniversityTitleCodeDTO> getFieldOfStudyByDepartmentCode(Long departmentCode);
}
