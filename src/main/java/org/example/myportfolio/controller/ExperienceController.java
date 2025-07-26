package org.example.myportfolio.controller;

import jakarta.persistence.PostRemove;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.myportfolio.request.ExperienceRequest;
import org.example.myportfolio.response.ExperienceResponse;
import org.example.myportfolio.service.ExperienceServis;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/experience")
public class ExperienceController {

    private final ExperienceServis experienceServis;


    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ExperienceResponse createExperience(@RequestBody @Valid ExperienceRequest experienceRequest) {
        return experienceServis.createExperience(experienceRequest);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        experienceServis.deleteExperience(id);
    }

    @GetMapping("/getExperience/{id}")
    public ExperienceResponse get(@PathVariable Long id) {
        return experienceServis.getExperience(id);
    }
    @GetMapping("/getExperiences")
    public List<ExperienceResponse> getExperiences() {
        return experienceServis.getExperiences();
    }

    @PatchMapping("/updateExperience/{id}")
    public ExperienceResponse update(@PathVariable Long id, @RequestBody ExperienceRequest experienceRequest) {
        return experienceServis.updateExperience(id, experienceRequest);
    }

    @PutMapping("/putUpdateExperience/{id}")
    public ExperienceResponse putUpdate(@PathVariable Long id, @RequestBody @Valid ExperienceRequest experienceRequest) {
        return experienceServis.putUpdateExperience(id, experienceRequest);
    }

}
