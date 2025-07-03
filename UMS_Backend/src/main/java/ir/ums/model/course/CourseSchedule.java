package ir.ums.model.course;

import ir.ums.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "COURSE_SCHEDULE")
public class CourseSchedule extends BaseEntity {

    @Column(name = "COURSE_START_TIME")
    private String courseStartTime; // 14:30

    @Column(name = "COURSE_END_TIME")
    private String courseEndTime; // 15:30

    @Column(name = "DAYS_OF_WEEK")
    private String dayOfWeek; // شنبه

    @Column(name = "COURSE_EXAM_TIME")
    private String courseExamTime; // 08:30

    @Column(name = "COURSE_EXAM_DATE")
    private String courseExamDate; // 1404/03/29

    @ManyToOne
    @JoinColumn(name = "FK_COURSE")
    private Course course;

}