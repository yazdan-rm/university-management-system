package ir.ums.model.course;

import ir.ums.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
@Table(name = "COURSE_PREREQUISITE")
public class CoursePrerequisite extends BaseEntity {

    @JoinColumn(name = "FK_COURSE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Course course;

    @JoinColumn(name = "FK_PREREQUISITE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Course prerequisite;

    @Column(name = "PREREQUISITE_TYPE")
    private String prerequisiteType; //  پيش نياز، همنياز و معادل
}
