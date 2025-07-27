package org.example.myportfolio.response;

import lombok.Data;
import org.example.myportfolio.model.Role;

import java.util.Set;

@Data
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private Role role;
}