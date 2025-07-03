package ir.ums.dao.user;

import ir.ums.constants.Messages;
import ir.ums.exception.CustomException;
import ir.ums.model.user.Student;
import ir.ums.repository.user.IStudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class StudentDaoImpl implements IStudentDao {

    private final IStudentRepository studentRepository;


    @Override
    public Student saveOrUpdateStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudentByKeycloakUserId(UUID userId) {
        studentRepository.deleteByKeycloakUserId(userId);
    }

    @Override
    public Student getStudentByKeycloakUserId(UUID userId) {
        Student student = studentRepository.getStudentByKeycloakUserId(userId);
//        if(student == null) throw new CustomException(Messages.USER_IS_NOT_ENROLLED_AS_STUDENT);
        return student;
    }
}
