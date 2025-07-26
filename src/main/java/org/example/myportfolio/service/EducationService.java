package org.example.myportfolio.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.myportfolio.dao.repository.EducationRepository;
import org.example.myportfolio.dao.repository.UserRepository;
import org.example.myportfolio.mapper.EducationMapper;
import org.example.myportfolio.request.EducationRequest;
import org.example.myportfolio.response.EducationResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@RequiredArgsConstructor
@Slf4j
public class EducationService {

    private final EducationRepository educationRepository;
    private final UserRepository userRepository;
    private final EducationMapper educationMapper;

    public EducationResponse createEducation(EducationRequest educationRequest) {
        var userId = getCurrentUserId();
        var existingEducation = educationRepository.existsByInstitutionAndFieldOfStudyAndStartDate(educationRequest.getInstitution(), educationRequest.getFieldOfStudy(), educationRequest.getStartDate());
        var user=userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        if (existingEducation) {
            throw new RuntimeException("Education already exists");
        }

        if (educationRequest.getStartDate().isAfter(educationRequest.getEndDate())
        ||  educationRequest.getStartDate().isEqual(educationRequest.getEndDate())) {
            throw new RuntimeException("Start date must be before end date");
        }

        var education = educationMapper.toEntity(educationRequest);
        education.setUser(user);
        var savedEducation = educationRepository.save(education);
        return educationMapper.toResponse(savedEducation);


    }

    private Long getCurrentUserId() {
        return (Long) ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest().getAttribute("userId");
    }

}
