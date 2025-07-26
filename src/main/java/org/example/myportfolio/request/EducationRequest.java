package org.example.myportfolio.request;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;


@Data
public class EducationRequest {
    @NotNull(message = "Institution name is required")
    private String institution;
    @NotNull(message = "Degree is required")
    private String degree;
    @NotNull(message = "Field of study is required")
    private String fieldOfStudy;
    @NotNull(message = "Start date is required")
    private LocalDate startDate;
    @NotNull(message = "End date is required")
    private LocalDate endDate;
}
