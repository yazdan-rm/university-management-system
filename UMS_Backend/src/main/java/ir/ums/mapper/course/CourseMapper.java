package ir.ums.mapper.course;

import ir.ums.dto.course.CourseDTO;
import ir.ums.model.course.Course;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CourseMapper {

    CourseDTO toDto(Course course);

    Course toEntity(CourseDTO courseDTO);

    void updateCourseFromDto(CourseDTO dto, @MappingTarget Course entity);
}
