package org.example.myportfolio.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.myportfolio.dao.repository.SkillsRepository;
import org.example.myportfolio.dao.repository.UserRepository;
import org.example.myportfolio.mapper.SkillsMapper;
import org.example.myportfolio.request.SkillsRequest;
import org.example.myportfolio.response.SkillsResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SkillsService {
    private final SkillsRepository skillsRepository;
    private final SkillsMapper skillsMapper;
    private final UserRepository userRepository;

    public SkillsResponse createSkill(SkillsRequest skillsRequest) {
        log.info("Actionlog.createSkill.start : ");

        var user1 = getCurrentUserId();
        var user = userRepository.findById(user1).orElseThrow(() -> new RuntimeException("User not found"));
        var existingSkill = skillsRepository.findByName(skillsRequest.getName());
        if (existingSkill.isPresent()) {
            throw new RuntimeException("Skill already exists");
        }

        var entity = skillsMapper.toEntity(skillsRequest);
        entity.setUser(user);
        var savedEntity = skillsRepository.save(entity);
        log.info("Actionlog.createSkill.end : ");
        return skillsMapper.toResponse(savedEntity);

    }

    public void deleteSkill(Long skillId) {
        log.info("Actionlog.deleteSkill.start : ");
        var userId = getCurrentUserId();
        var skill = skillsRepository.findById(skillId).orElseThrow(() -> new RuntimeException("Skill not found"));

        if (!skill.getUser().getId().equals(userId)) {
            throw new RuntimeException("You are not authorized to delete this skill");
        }
        skillsRepository.delete(skill);
        log.info("Actionlog.deleteSkill.end : ");
    }

    public SkillsResponse getSkill(Long skillId) {
        log.info("Actionlog.getSkill.start : ");
        var userId = getCurrentUserId();
        var skill = skillsRepository.findById(skillId).orElseThrow(() -> new RuntimeException("Skill not found"));
        if (!skill.getUser().getId().equals(userId)) {
            throw new RuntimeException("You are not authorized to view this skill");
        }
        log.info("Actionlog.getSkill.end : ");
        return skillsMapper.toResponse(skill);
    }

    public List<SkillsResponse> getAllSkills() {
        log.info("Actionlog.getSkills.start : ");
        var userId = getCurrentUserId();
        var skills = skillsRepository.findAllByUserId(userId);
        if (skills.isEmpty()) {
            throw new RuntimeException("No skills found");
        }
        var list = skills.stream().map(skillsMapper::toResponse).toList();
        log.info("Actionlog.getSkills.end : ");
        return list;
    }


    public SkillsResponse updateSkill(Long skillId, SkillsRequest skillsRequest) {
        log.info("Actionlog.updateSkill.start : ");
        var userId = getCurrentUserId();
        var skill = skillsRepository.findById(skillId).orElseThrow(() -> new RuntimeException("Skill not found"));
        if (!skill.getUser().getId().equals(userId)) {
            throw new RuntimeException("You are not authorized to update this skill");
        }

        if(skillsRequest.getName() != null) {
            skill.setName(skillsRequest.getName());
        }
        if(skillsRequest.getLevel() != null) {
            skill.setLevel(skillsRequest.getLevel());
        }

        var savedSkill = skillsRepository.save(skill);
        log.info("Actionlog.updateSkill.end : ");
        return skillsMapper.toResponse(savedSkill);
    }


    private Long getCurrentUserId() {
        return (Long) ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest().getAttribute("userId");
    }


}
