package org.example.myportfolio.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ExperienceRequest {

    @NotNull(message = "Company name cannot be null")
    @Size(min = 1, message = "Company name cannot be blank")
    private String company;

    @NotNull(message = "Position cannot be null")
    @Size(min = 1, message = "Position cannot be blank")
    private String position;

    @NotNull(message = "Description cannot be null")
    @Size(min = 1, message = "Description cannot be blank")
    private String description;

    @NotNull(message = "Start date cannot be null")
    private LocalDate startDate;

    private LocalDate endDate;
}
