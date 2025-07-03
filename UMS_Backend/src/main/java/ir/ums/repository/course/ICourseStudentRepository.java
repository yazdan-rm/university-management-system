package ir.ums.repository.course;

import ir.ums.model.course.CourseStudent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ICourseStudentRepository extends JpaRepository<CourseStudent, UUID> {

    List<CourseStudent> getCourseStudentByStudent_IdAndScoreResult(UUID studentId, Boolean scoreResult);
}
