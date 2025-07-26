package org.example.myportfolio.dao.repository;

import org.example.myportfolio.dao.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EducationRepository extends JpaRepository<Education, Long> {
    boolean existsByInstitutionAndFieldOfStudyAndStartDate(String institution, String fieldOfStudy, LocalDate startDate);
    List<Education> findAllByUserId(Long userId);
}
