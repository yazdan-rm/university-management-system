package ir.ums.dto.university;

import ir.ums.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UniversityDTO extends BaseDTO {

    private String university;
    private Long universityCode;
    private String college;
    private Long collegeCode;
    private String department;
    private Long departmentCode;
    private String fieldOfStudy;
    private Long fieldOfStudyCode;
}
