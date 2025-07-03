package ir.ums.service.university;

import ir.ums.dao.university.IUniversityDao;
import ir.ums.dto.university.UniversityTitleCodeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UniversityServiceImpl implements IUniversityService {

    private final IUniversityDao universityDao;

    @Override
    public List<UniversityTitleCodeDTO> getAllColleges() {
        return universityDao.getAllColleges();
    }

    @Override
    public List<UniversityTitleCodeDTO> getDepartmentsByCollegeCode(Long collegeCode) {
        return universityDao.getDepartmentsByCollegeCode(collegeCode);
    }

    @Override
    public List<UniversityTitleCodeDTO> getFieldOfStudyByDepartmentCode(Long departmentCode) {
        return universityDao.getFieldOfStudyByDepartmentCode(departmentCode);
    }
}
