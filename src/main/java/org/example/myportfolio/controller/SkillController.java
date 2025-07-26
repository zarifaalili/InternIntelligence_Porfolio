package org.example.myportfolio.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.myportfolio.request.SkillsRequest;
import org.example.myportfolio.response.SkillsResponse;
import org.example.myportfolio.service.SkillsService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/skill")
@RequiredArgsConstructor
public class SkillController {
    private final SkillsService skillsService;


    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public SkillsResponse create(@RequestBody @Valid  SkillsRequest request) {
        return skillsService.createSkill(request);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        skillsService.deleteSkill(id);
    }

    @GetMapping("/getSkill/{id}")
    public SkillsResponse get(@PathVariable Long id) {
        return skillsService.getSkill(id);
    }

    @GetMapping("/getAllSkills")
    public List<SkillsResponse> getAll() {
        return skillsService.getAllSkills();
    }

    @PatchMapping("/updateSkill/{id}")
    public SkillsResponse update(@PathVariable Long id, @RequestBody SkillsRequest request) {
        return skillsService.updateSkill(id, request);
    }

}
