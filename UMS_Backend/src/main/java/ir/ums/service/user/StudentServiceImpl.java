package ir.ums.service.user;

import ir.ums.dao.university.IUniversityDao;
import ir.ums.dao.user.IStudentDao;
import ir.ums.dto.user.StudentDTO;
import ir.ums.mapper.user.StudentMapper;
import ir.ums.model.university.University;
import ir.ums.model.user.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Lazy
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements IStudentService {

    private final StudentMapper studentMapper;
    private final IStudentDao studentDao;
    private final IUniversityDao universityDao;

    @Override
    public void saveStudent(StudentDTO studentDTO) {
        University university = universityDao.getUniversityByCodes(
                studentDTO.getUniversity().getCollegeCode(),
                studentDTO.getUniversity().getDepartmentCode(),
                studentDTO.getUniversity().getFieldOfStudyCode()
        );
        Student student = studentMapper.toEntity(studentDTO);
        student.setUniversity(university);
        studentDao.saveOrUpdateStudent(student);
    }

    @Override
    public Student updateStudent(UUID userId, StudentDTO studentDTO) {
        Student existStudent = studentDao.getStudentByKeycloakUserId(userId);
        studentMapper.updateStudentFromDto(studentDTO, existStudent);
        return studentDao.saveOrUpdateStudent(existStudent);
    }

    @Override
    public void deleteStudentByKeycloakUserId(UUID userId) {
        studentDao.deleteStudentByKeycloakUserId(userId);
    }

    @Override
    public StudentDTO getStudentByKeycloakUserId(UUID userId) {
        Student student = studentDao.getStudentByKeycloakUserId(userId);
        return studentMapper.toDto(student);
    }

    @Override
    public Student getCurrentStudentLoggedIn() {
        var auth = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = auth.getToken();
        var userKeycloakUID = UUID.fromString(jwt.getSubject());
        return studentDao.getStudentByKeycloakUserId(userKeycloakUID);
    }
}
