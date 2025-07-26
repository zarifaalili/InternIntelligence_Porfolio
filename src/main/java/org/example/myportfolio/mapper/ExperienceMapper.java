package org.example.myportfolio.mapper;

import org.example.myportfolio.dao.entity.Experience;
import org.example.myportfolio.request.ExperienceRequest;
import org.example.myportfolio.response.ExperienceResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExperienceMapper {

    Experience toEntity(ExperienceRequest experienceRequest);

    ExperienceResponse toResponse(Experience experience);
}
