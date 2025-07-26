package org.example.myportfolio.dao.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.myportfolio.model.Role;

import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

}

