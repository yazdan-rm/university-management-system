package ir.ums.repository.course;

import ir.ums.model.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ICourseRepository extends JpaRepository<Course, UUID> {
}
