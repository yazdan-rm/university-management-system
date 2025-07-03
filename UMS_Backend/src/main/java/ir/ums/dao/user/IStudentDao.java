package ir.ums.dao.user;

import ir.ums.model.user.Student;

import java.util.UUID;

public interface IStudentDao {

    Student saveOrUpdateStudent(Student student);
    void deleteStudentByKeycloakUserId(UUID userId);
    Student getStudentByKeycloakUserId(UUID userId);
}
