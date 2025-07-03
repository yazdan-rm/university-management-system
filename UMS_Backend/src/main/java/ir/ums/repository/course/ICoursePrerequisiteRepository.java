package ir.ums.repository.course;

import ir.ums.model.course.Course;
import ir.ums.model.course.CoursePrerequisite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ICoursePrerequisiteRepository extends JpaRepository<CoursePrerequisite, UUID> {

    List<CoursePrerequisite> findCoursePrerequisiteByCourse_idAndPrerequisiteType(UUID courseId, String prerequisiteType);

    List<CoursePrerequisite> findByPrerequisite(Course prerequisite);

    List<CoursePrerequisite> findCoursePrerequisiteByCourse_Id(UUID courseId);

    UUID course(Course course);
}
