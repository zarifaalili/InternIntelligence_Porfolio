package org.example.myportfolio.mapper;

import org.example.myportfolio.dao.entity.Education;
import org.example.myportfolio.request.EducationRequest;
import org.example.myportfolio.response.EducationResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EducationMapper {

    Education toEntity(EducationRequest educationRequest);


    EducationResponse toResponse(Education education);
}
