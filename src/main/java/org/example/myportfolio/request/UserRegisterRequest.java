package org.example.myportfolio.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterRequest {

    @NotBlank
    private String username;
    @NotBlank
    @Email
    private String email;
    @NotBlank @Size(min = 6)
    private String password;

    @NotBlank
    @Size(min = 8)
    private String confirmedPassword;



}
