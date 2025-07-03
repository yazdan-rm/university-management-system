package ir.ums.repository.user;

import ir.ums.model.user.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IStudentRepository extends JpaRepository<Student, Integer> {

    void deleteByKeycloakUserId(UUID keycloakUserId);

    Student getStudentByKeycloakUserId(UUID keycloakUserId);
}
