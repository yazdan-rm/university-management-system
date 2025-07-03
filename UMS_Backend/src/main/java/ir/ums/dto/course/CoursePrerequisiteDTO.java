package ir.ums.dto.course;

import ir.ums.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
public class CoursePrerequisiteDTO extends BaseDTO {

    private UUID courseId;
    private String courseName;
    private UUID prerequisiteId;
    private String prerequisiteName;
    private String prerequisiteType;

    public CoursePrerequisiteDTO(UUID id, LocalDateTime createDate, UUID courseId, String courseName, UUID prerequisiteId, String prerequisiteName, String prerequisiteType) {
        super.setId(id);
        super.setCreateDate(createDate);
        this.courseId = courseId;
        this.courseName = courseName;
        this.prerequisiteId = prerequisiteId;
        this.prerequisiteName = prerequisiteName;
        this.prerequisiteType = prerequisiteType;
    }
}
