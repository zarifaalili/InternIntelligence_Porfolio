package org.example.myportfolio.dao.repository;

import org.example.myportfolio.dao.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
   boolean findByTitle(String title);

   List<Project> findAllByUserId(Long userId);
   void deleteAllByUserId(Long userId);

}
