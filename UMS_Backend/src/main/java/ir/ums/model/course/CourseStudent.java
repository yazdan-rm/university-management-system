package ir.ums.model.course;

import ir.ums.model.BaseEntity;
import ir.ums.model.user.Student;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "COURSE_STUDENT")
public class CourseStudent extends BaseEntity {

    @Column(name = "COURSE_SCORE")
    private Float courseScore; // زیر 10 رد ، 10 یا بالای 10 قبول

    @Column(name = "SCORE_RESULT")
    private Boolean scoreResult; // null: نامشخص , true: قبول , false: رد

    @Column(name = "SCORE_STATUS")
    private boolean scoreStatus; // false: اعلام نشده , true: اعلام شده

    @ManyToOne
    @JoinColumn(name = "FK_COURSE")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "FK_STUDENT")
    private Student student;
}