package org.example.myportfolio.controller;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.myportfolio.request.EducationRequest;
import org.example.myportfolio.request.EducationUpdateRequest;
import org.example.myportfolio.response.EducationResponse;
import org.example.myportfolio.service.EducationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    @PatchMapping("/updateEducation/{educationId}")
    @PreAuthorize("hasRole('USER')")
    public EducationResponse updateEducation(@PathVariable Long educationId, @RequestBody  EducationRequest educationRequest) {
        return educationService.updatedEducation(educationId, educationRequest);
    }

    @GetMapping("/getEducations")
    @PreAuthorize("hasRole('USER')")
    public List<EducationResponse> getEducations() {
        return educationService.getEducations();
    }

    @GetMapping("/getEducation/{educationId}")
    @PreAuthorize("hasRole('USER')")
    public EducationResponse getEducation(@PathVariable Long educationId) {
        return educationService.getEducation(educationId);
    }

    @DeleteMapping("/deleteEducation/{educationId}")
    @PreAuthorize("hasRole('USER')")
    public void deleteEducation(@PathVariable Long educationId) {
        educationService.deleteEducation(educationId);
    }
}

