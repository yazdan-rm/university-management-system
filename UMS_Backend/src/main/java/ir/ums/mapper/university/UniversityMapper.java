package ir.ums.mapper.university;

import ir.ums.dto.university.UniversityDTO;
import ir.ums.model.university.University;
import org.mapstruct.Mapper;

@Mapper
public interface UniversityMapper {

    UniversityDTO toDto(University university);

    University toEntity(UniversityDTO universityDTO);
}
