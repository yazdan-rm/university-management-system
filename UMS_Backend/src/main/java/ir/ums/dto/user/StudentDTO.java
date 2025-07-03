package ir.ums.dto.user;

import ir.ums.dto.BaseDTO;
import ir.ums.dto.university.UniversityDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class StudentDTO extends BaseDTO {

    private String semester;

    private Integer educationalLevel;

    private String nationalCode;

    private UUID keycloakUserId;

    private String studentUiSetting;

    private Integer totalEnrolledCourseUnits;

    private UniversityDTO university;
}
