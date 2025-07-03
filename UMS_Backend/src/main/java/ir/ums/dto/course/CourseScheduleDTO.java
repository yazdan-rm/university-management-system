package ir.ums.dto.course;

import ir.ums.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class CourseScheduleDTO extends BaseDTO {
    private UUID courseId;
    private String courseStartTime;
    private String courseEndTime;
    private String dayOfWeek;
    private String courseExamTime;
    private String courseExamDate;
}

