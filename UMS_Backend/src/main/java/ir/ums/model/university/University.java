package ir.ums.model.university;

import ir.ums.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "UNIVERSITY")
public class University extends BaseEntity {

    @Column(name = "UNIVERSITY_NAME")
    private String university;

    @Column(name = "UNIVERSITY_CODE")
    private Long universityCode;

    @Column(name = "COLLEGE_NAME")
    private String college;

    @Column(name = "COLLEGE_CODE")
    private Long collegeCode;

    @Column(name = "DEPARTMENT_NAME")
    private String department;

    @Column(name = "DEPARTMENT_CODE")
    private Long departmentCode;

    @Column(name = "FIELD_OF_STUDY")
    private String fieldOfStudy;

    @Column(name = "FIELD_OF_STUDY_CODE")
    private Long fieldOfStudyCode;
}
