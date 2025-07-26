package org.example.myportfolio.mapper;

import org.example.myportfolio.dao.entity.Skills;
import org.example.myportfolio.request.SkillsRequest;
import org.example.myportfolio.response.SkillsResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SkillsMapper {

    Skills toEntity(SkillsRequest skillsRequest);

    SkillsResponse toResponse(Skills skills);
}
