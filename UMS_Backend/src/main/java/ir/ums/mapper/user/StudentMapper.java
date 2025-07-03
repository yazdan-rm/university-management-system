package ir.ums.mapper.user;

import ir.ums.dto.user.StudentDTO;
import ir.ums.mapper.university.UniversityMapper;
import ir.ums.model.user.Student;
import org.mapstruct.*;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = UniversityMapper.class)
public interface StudentMapper {

    StudentDTO toDto(Student student);

    Student toEntity(StudentDTO studentDTO);

    void updateStudentFromDto(StudentDTO dto, @MappingTarget Student entity);
}
