package org.example.myportfolio.dao.repository;

import org.example.myportfolio.dao.entity.ResetCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResetCodeRepository extends JpaRepository<ResetCode, Long> {
    void deleteByEmail(String email);
    Optional<ResetCode> findByEmailAndCode(String email, String code);
}
