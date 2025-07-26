package org.example.myportfolio.dao.repository;

import org.example.myportfolio.dao.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.expression.spel.ast.OpAnd;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    Optional<Experience> findByCompanyAndPositionAndStartDateAndEndDate(String company, String position, LocalDate startDate, LocalDate endDate);
    List<Experience> findAllByUserId(Long userId);
}
