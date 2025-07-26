package org.example.myportfolio.dao.repository;

import org.example.myportfolio.dao.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
