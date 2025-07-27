package org.example.myportfolio.dao.repository;

import org.example.myportfolio.dao.entity.Skills;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SkillsRepository extends JpaRepository<Skills, Long> {
    Optional<Skills> findByName(String name);
    List<Skills> findAllByUserId(Long userId);
    void deleteAllByUserId(Long userId);
}
