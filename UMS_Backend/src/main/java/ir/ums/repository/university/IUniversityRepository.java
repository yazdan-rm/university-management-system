package ir.ums.repository.university;

import ir.ums.model.university.University;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IUniversityRepository extends JpaRepository<University, UUID> {
}
