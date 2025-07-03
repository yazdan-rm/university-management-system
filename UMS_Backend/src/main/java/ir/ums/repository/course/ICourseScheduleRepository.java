package ir.ums.repository.course;

import ir.ums.model.course.Course;
import ir.ums.model.course.CourseSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ICourseScheduleRepository extends JpaRepository<CourseSchedule, UUID> {

    List<CourseSchedule> findByCourse(Course course);
}
