package ir.ums.dao.university;

import ir.ums.dto.university.UniversityTitleCodeDTO;
import ir.ums.model.university.University;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UniversityDaoImpl implements IUniversityDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public University getUniversityByCodes(Long collegeCode, Long departmentCode, Long fieldOfStudyCode) {
        return entityManager.createQuery("""
                        from University u
                        where u.collegeCode = :collegeCode
                        and   u.departmentCode = :departmentCode
                        and   u.fieldOfStudyCode = :fieldOfStudyCode
                        """, University.class)
                .setParameter("collegeCode", collegeCode)
                .setParameter("departmentCode", departmentCode)
                .setParameter("fieldOfStudyCode", fieldOfStudyCode)
                .getSingleResult();
    }

    @Override
    public List<UniversityTitleCodeDTO> getAllColleges() {
        return entityManager.createQuery("""
                        select distinct new ir.ums.dto.university.UniversityTitleCodeDTO( u.college, u.collegeCode)
                        from University u
                        order by u.college, u.collegeCode
                        """, UniversityTitleCodeDTO.class)
                .getResultList();
    }

    @Override
    public List<UniversityTitleCodeDTO> getDepartmentsByCollegeCode(Long collegeCode) {
        return entityManager.createQuery("""
                        select distinct new ir.ums.dto.university.UniversityTitleCodeDTO(u.department, u.departmentCode)
                        from University u
                        where u.collegeCode = :collegeCode
                        order by u.department, u.departmentCode
                        """, UniversityTitleCodeDTO.class)
                .setParameter("collegeCode", collegeCode)
                .getResultList();
    }

    @Override
    public List<UniversityTitleCodeDTO> getFieldOfStudyByDepartmentCode(Long departmentCode) {
        return entityManager.createQuery("""
                        select distinct new ir.ums.dto.university.UniversityTitleCodeDTO(u.fieldOfStudy, u.fieldOfStudyCode)
                        from University u
                        where u.departmentCode = :departmentCode
                        order by u.fieldOfStudy, u.fieldOfStudyCode
                        """, UniversityTitleCodeDTO.class)
                .setParameter("departmentCode", departmentCode)
                .getResultList();
    }
}
