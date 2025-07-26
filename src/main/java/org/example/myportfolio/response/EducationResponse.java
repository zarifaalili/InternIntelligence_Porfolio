package org.example.myportfolio.response;


import lombok.Data;

import java.time.LocalDate;

@Data
public class EducationResponse {
    private Long id;
    private String institution;
    private String degree;
    private String fieldOfStudy;
    private LocalDate startDate;
    private LocalDate endDate;

}
