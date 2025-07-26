package org.example.myportfolio.dao.repository;

import org.example.myportfolio.dao.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
}
