package ir.ums.service.user;

import ir.ums.dto.user.StudentDTO;
import ir.ums.model.user.Student;

import java.util.UUID;

public interface IStudentService {

    void saveStudent(StudentDTO studentDTO);
    Student updateStudent(UUID userId, StudentDTO studentDTO);
    void deleteStudentByKeycloakUserId(UUID userId);
    StudentDTO getStudentByKeycloakUserId(UUID userId);
    Student getCurrentStudentLoggedIn();
}
