package org.example.myportfolio.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.myportfolio.dao.repository.EducationRepository;
import org.example.myportfolio.dao.repository.UserRepository;
import org.example.myportfolio.mapper.EducationMapper;
import org.example.myportfolio.request.EducationRequest;
import org.example.myportfolio.request.EducationUpdateRequest;
import org.example.myportfolio.response.EducationResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EducationService {

    private final EducationRepository educationRepository;
    private final UserRepository userRepository;
    private final EducationMapper educationMapper;

    public EducationResponse createEducation(EducationRequest educationRequest) {
        log.info("Actionlog.createEducation.start : ");
        var userId = getCurrentUserId();
        var existingEducation = educationRepository.existsByInstitutionAndFieldOfStudyAndStartDate(educationRequest.getInstitution(), educationRequest.getFieldOfStudy(), educationRequest.getStartDate());
        var user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if (existingEducation) {
            throw new RuntimeException("Education already exists");
        }

        if (educationRequest.getStartDate().isAfter(educationRequest.getEndDate())
                || educationRequest.getStartDate().isEqual(educationRequest.getEndDate())) {
            throw new RuntimeException("Start date must be before end date");
        }

        var education = educationMapper.toEntity(educationRequest);
        education.setUser(user);
        var savedEducation = educationRepository.save(education);
        log.info("Actionlog.createEducation.end : ");
        return educationMapper.toResponse(savedEducation);


    }

    public EducationResponse updatedEducation(Long educationId, EducationRequest request) {
        log.info("Actionlog.updateEducation.start : ");
        var userId = getCurrentUserId();
        var education = educationRepository.findById(educationId).orElseThrow(() -> new RuntimeException("Education not found"));
        if (!education.getUser().getId().equals(userId)) {
            throw new RuntimeException("You are not authorized to update this education");
        }
        if (request.getStartDate() != null && request.getEndDate() != null) {
            if (request.getStartDate().isAfter(request.getEndDate()) ||
                    request.getStartDate().isEqual(request.getEndDate())) {
                throw new RuntimeException("Start date must be before end date");
            }
        }


        if (request.getInstitution() != null) {
            education.setInstitution(request.getInstitution());
        }
        if (request.getDegree() != null) {
            education.setDegree(request.getDegree());
        }
        if (request.getFieldOfStudy() != null) {
            education.setFieldOfStudy(request.getFieldOfStudy());
        }
        if (request.getStartDate() != null) {
            education.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            education.setEndDate(request.getEndDate());
        }
        var saved = educationRepository.save(education);
        log.info("Actionlog.updateEducation.end : ");
        return educationMapper.toResponse(saved);
    }


    public List<EducationResponse> getEducations() {
        log.info("Actionlog.getEducations.start : ");
        var userId = getCurrentUserId();
        var educations = educationRepository.findAllByUserId(userId);
        if (educations.isEmpty()) {
            throw new RuntimeException("No educations found");
        }
        var list = educations.stream().map(educationMapper::toResponse).toList();
        log.info("Actionlog.getEducations.end : ");
        return list;
    }

    public EducationResponse getEducation(Long educationId) {
        log.info("Actionlog.getEducation.start : ");
        var userId = getCurrentUserId();
        var education = educationRepository.findById(educationId).orElseThrow(() -> new RuntimeException("Education not found"));
        if (!education.getUser().getId().equals(userId)) {
            throw new RuntimeException("You are not authorized to view this education");
        }
        log.info("Actionlog.getEducation.end : ");
        return educationMapper.toResponse(education);
    }

    public void deleteEducation(Long educationId) {
        log.info("Actionlog.deleteEducation.start : ");
        var userId = getCurrentUserId();
        var education = educationRepository.findById(educationId).orElseThrow(() -> new RuntimeException("Education not found"));
        if (!education.getUser().getId().equals(userId)) {
            throw new RuntimeException("You are not authorized to delete this education");
        }
        educationRepository.delete(education);
        log.info("Actionlog.deleteEducation.end : ");
    }


    private Long getCurrentUserId() {
        return (Long) ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest().getAttribute("userId");
    }

}
