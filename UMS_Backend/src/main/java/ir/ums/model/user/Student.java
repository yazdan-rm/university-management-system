package ir.ums.model.user;

import ir.ums.model.BaseEntity;
import ir.ums.model.university.University;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "STUDENT")
public class Student extends BaseEntity {

    // Can be more data about student like location, certificate of high school, father name,
    // but I keep it simple for learning purposes
    // STUDENT_ID is username of keycloak

    @Column(name = "SEMESTER")
    private String semester;

    @Column(name = "EDUCATIONAL_LEVEL")
    private Integer educationalLevel;

    @Column(name = "NATIONAL_CODE")
    private String nationalCode;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "FK_KEYCLOAK_USER_ID", length = 36, columnDefinition = "varchar2(36)")
    private UUID keycloakUserId;

    @Lob
    @Column(name = "STUDENT_UI_SETTING")
    private String studentUiSetting;

    @ManyToOne
    @JoinColumn(name = "FK_UNIVERSITY")
    private University university;
}