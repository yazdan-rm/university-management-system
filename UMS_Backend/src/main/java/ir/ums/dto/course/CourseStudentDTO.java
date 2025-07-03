package ir.ums.dto.course;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class CourseStudentDTO {
    private UUID courseId;
    private UUID keycloakUserId;
    private Float courseScore;
    private Boolean scoreResult;
    private boolean scoreStatus;
}
