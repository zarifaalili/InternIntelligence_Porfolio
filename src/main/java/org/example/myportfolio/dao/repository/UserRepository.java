package org.example.myportfolio.dao.repository;

import org.example.myportfolio.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
