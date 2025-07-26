package org.example.myportfolio.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ExperienceResponse {
    private Long id;
    private String company;
    private String position;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
}
