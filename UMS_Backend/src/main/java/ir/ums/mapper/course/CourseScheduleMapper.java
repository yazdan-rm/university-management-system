package ir.ums.mapper.course;

import ir.ums.dto.course.CourseScheduleDTO;
import ir.ums.model.course.CourseSchedule;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CourseScheduleMapper {

    CourseScheduleDTO toDto(CourseSchedule course);

    List<CourseScheduleDTO> toDtoList(List<CourseSchedule> courses);

    CourseSchedule toEntity(CourseScheduleDTO courseScheduleDTO);

    void updateCourseScheduleFromDto(CourseScheduleDTO dto, @MappingTarget CourseSchedule entity);
}
