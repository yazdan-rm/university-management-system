package ir.ums.dao.course;

import ir.ums.repository.course.ICourseStudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseStudentDaoImplTest {

    @Autowired
    ICourseStudentRepository courseStudentRepository;

    @Test
    void getCoursesByStudentIdAndScoreResult() {
        var courseStudents = courseStudentRepository.getCourseStudentByStudent_IdAndScoreResult(
                UUID.fromString("c0a80168-97ab-177e-8197-ad12de810000"), Boolean.FALSE);

        assertEquals(0, courseStudents.size());
    }
}