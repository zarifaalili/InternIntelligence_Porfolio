package org.example.myportfolio.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.myportfolio.dao.repository.ExperienceRepository;
import org.example.myportfolio.dao.repository.UserRepository;
import org.example.myportfolio.mapper.ExperienceMapper;
import org.example.myportfolio.request.ExperienceRequest;
import org.example.myportfolio.response.ExperienceResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExperienceServis {
    private final ExperienceRepository experienceRepository;
    private final ExperienceMapper experienceMapper;
    private final UserRepository userRepository;

    public ExperienceResponse createExperience(ExperienceRequest experienceRequest) {
        log.info("Actionlog.createExperience.start : ");
        Long userId = getCurrentUserId();
        var existingExperience = experienceRepository.findByCompanyAndPositionAndStartDateAndEndDate(experienceRequest.getCompany(), experienceRequest.getPosition(), experienceRequest.getStartDate(), experienceRequest.getEndDate());

        var user=userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if(existingExperience.isPresent()){
            throw new RuntimeException("Experience already exists");
        }

        if(experienceRequest.getStartDate().isAfter(experienceRequest.getEndDate())
                || experienceRequest.getStartDate().isEqual(experienceRequest.getEndDate())) {
            throw new RuntimeException("Start date must be before end date");
        }

        var experience = experienceMapper.toEntity(experienceRequest);
        experience.setUser(user);
        var savedExperience = experienceRepository.save(experience);
        log.info("Actionlog.createExperience.end : ");
        return experienceMapper.toResponse(savedExperience);

    }


    public void deleteExperience(Long experienceId) {
        log.info("Actionlog.deleteExperience.start : ");
        var userId=getCurrentUserId();
        var experience = experienceRepository.findById(experienceId).orElseThrow(() -> new RuntimeException("Experience not found"));
        if(!experience.getUser().getId().equals(userId)){
            throw new RuntimeException("You are not authorized to delete this experience");
        }
        experienceRepository.deleteById(experienceId);
        log.info("Actionlog.deleteExperience.end : ");
    }

    public ExperienceResponse getExperience(Long experienceId) {
        log.info("Actionlog.getExperience.start : ");
        var userId=getCurrentUserId();
        var experience = experienceRepository.findById(experienceId).orElseThrow(() -> new RuntimeException("Experience not found"));
        if(!experience.getUser().getId().equals(userId)){
            throw new RuntimeException("You are not authorized to view this experience");
        }
        log.info("Actionlog.getExperience.end : ");
        return experienceMapper.toResponse(experience);
    }
    public List<ExperienceResponse> getExperiences() {
        log.info("Actionlog.getExperiences.start : ");
        var userId=getCurrentUserId();
        var experiences = experienceRepository.findAllByUserId(userId);

        if (experiences.isEmpty()) {
            throw new RuntimeException("No experiences found");
        }
        var list = experiences.stream().map(experienceMapper::toResponse).toList();
        log.info("Actionlog.getExperiences.end : ");
        return list;
    }

    public ExperienceResponse updateExperience(Long experienceId, ExperienceRequest experienceRequest) {
        log.info("Actionlog.updateExperience.start : ");
        var userId=getCurrentUserId();
        var experience = experienceRepository.findById(experienceId).orElseThrow(() -> new RuntimeException("Experience not found"));
        if(!experience.getUser().getId().equals(userId)){
            throw new RuntimeException("You are not authorized to update this experience");
        }
        if (experienceRequest.getStartDate() != null && experienceRequest.getEndDate() != null) {
            if (experienceRequest.getStartDate().isAfter(experienceRequest.getEndDate()) ||
                    experienceRequest.getStartDate().isEqual(experienceRequest.getEndDate())) {
                throw new RuntimeException("Start date must be before end date");
            }
        }
        if(experienceRequest.getCompany() != null){
            experience.setCompany(experienceRequest.getCompany());
        }
        if(experienceRequest.getPosition() != null){
            experience.setPosition(experienceRequest.getPosition());
        }
        if(experienceRequest.getDescription() != null){
            experience.setDescription(experienceRequest.getDescription());
        }
        if(experienceRequest.getStartDate() != null){
            experience.setStartDate(experienceRequest.getStartDate());
        }
        if(experienceRequest.getEndDate() != null){
            experience.setEndDate(experienceRequest.getEndDate());
        }

        var savedExperience = experienceRepository.save(experience);
        log.info("Actionlog.updateExperience.end : ");
        return experienceMapper.toResponse(savedExperience);
    }

    public ExperienceResponse putUpdateExperience(Long experienceId, ExperienceRequest experienceRequest) {
        log.info("Actionlog.putUpdateExperience.start : ");
        var userId=getCurrentUserId();
        var experience = experienceRepository.findById(experienceId).orElseThrow(() -> new RuntimeException("Experience not found"));
        if(!experience.getUser().getId().equals(userId)){
            throw new RuntimeException("You are not authorized to update this experience");
        }
        if(experienceRequest.getStartDate().isAfter(experienceRequest.getEndDate())
                || experienceRequest.getStartDate().isEqual(experienceRequest.getEndDate())) {
            throw new RuntimeException("Start date must be before end date");
        }
        experience.setCompany(experienceRequest.getCompany());
        experience.setPosition(experienceRequest.getPosition());
        experience.setDescription(experienceRequest.getDescription());
        experience.setStartDate(experienceRequest.getStartDate());
        experience.setEndDate(experienceRequest.getEndDate());
        var savedExperience = experienceRepository.save(experience);
        log.info("Actionlog.putUpdateExperience.end : ");
        return experienceMapper.toResponse(savedExperience);
    }


    private Long getCurrentUserId() {
        return (Long) ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest().getAttribute("userId");
    }

}
