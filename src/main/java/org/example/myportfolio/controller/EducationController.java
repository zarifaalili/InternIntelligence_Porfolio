package org.example.myportfolio.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.myportfolio.request.EducationRequest;
import org.example.myportfolio.response.EducationResponse;
import org.example.myportfolio.service.EducationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/education")
@RequiredArgsConstructor
public class EducationController {
    private final EducationService educationService;


    @PostMapping("/createEducation")
    @PreAuthorize("hasRole('USER')")
    public EducationResponse createEducation(@RequestBody @Valid EducationRequest educationRequest) {
        return educationService.createEducation(educationRequest);
    }
}
