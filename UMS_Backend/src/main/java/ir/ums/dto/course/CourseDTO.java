package ir.ums.dto.course;

import ir.ums.dto.BaseDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CourseDTO extends BaseDTO {

    @NotNull
    private String semester;

    @NotNull
    private Integer courseUnits;

    @NotNull
    private Integer allowedGenders;

    @NotNull
    private String courseName;

    @NotNull
    private String instructorName;

    @NotNull
    private Integer capacity;

    @NotNull
    private Integer educationalLevel;

    @NotNull
    private String location;

    @NotNull
    private Long collegeCode;

    @NotNull
    private Long departmentCode;

    @NotNull
    private Long fieldOfStudyCode;

    private String collegeName;

    private String departmentName;

    private String fieldOfStudy;

    private String scheduleTime;

    private String examTime;

    private String prerequisiteCourses;
}