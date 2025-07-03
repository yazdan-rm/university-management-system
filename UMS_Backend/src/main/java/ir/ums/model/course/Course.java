package ir.ums.model.course;

import ir.ums.model.BaseEntity;
import ir.ums.model.university.University;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "COURSE")
public class Course extends BaseEntity {

    @Column(name = "SEMESTER")
    private String semester;

    @Column(name = "COURSE_UNITS")
    private Integer courseUnits;

    @Column(name = "PV_ALLOWED_GENDERS")
    private Integer allowedGenders; // male, female, mixed

    @Column(name = "COURSE_NAME")
    private String courseName;

    @Column(name = "INSTRUCTOR_NAME")
    private String instructorName;

    @Column(name = "CAPACITY")
    private Integer capacity;

    @Column(name = "ENROLLED_COUNT")
    private Integer enrolledCount;

    @Column(name = "EDUCATIONAL_LEVEL")
    private Integer educationalLevel;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "HAS_PREREQUISITE_COURSE")
    private boolean hasPrerequisiteCourse;

    @ManyToOne
    @JoinColumn(name = "FK_UNIVERSITY")
    private University university;
}
