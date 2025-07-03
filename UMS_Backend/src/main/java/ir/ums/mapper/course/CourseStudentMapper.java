package ir.ums.mapper.course;

import ir.ums.dto.course.CourseStudentDTO;
import ir.ums.model.course.CourseStudent;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CourseStudentMapper {

    CourseStudentDTO toDto(CourseStudent courseStudent);

    CourseStudent toEntity(CourseStudentDTO courseStudentDTO);

    void updateCourseStudentFromDto(CourseStudentDTO courseStudentDTO, @MappingTarget CourseStudent courseStudent);
}
