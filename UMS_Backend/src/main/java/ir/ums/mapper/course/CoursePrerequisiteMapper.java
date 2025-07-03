package ir.ums.mapper.course;

import ir.ums.dto.course.CoursePrerequisiteDTO;
import ir.ums.model.course.CoursePrerequisite;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface CoursePrerequisiteMapper {

    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "course.courseName", target = "courseName")
    @Mapping(source = "prerequisite.id", target = "prerequisiteId")
    @Mapping(source = "prerequisite.courseName", target = "prerequisiteName")
    CoursePrerequisiteDTO toDto(CoursePrerequisite entity);

    List<CoursePrerequisiteDTO> toDtoList(List<CoursePrerequisite> entities);
}
