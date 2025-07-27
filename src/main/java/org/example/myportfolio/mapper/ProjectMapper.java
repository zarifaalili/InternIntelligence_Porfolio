package org.example.myportfolio.mapper;

import org.example.myportfolio.dao.entity.Project;
import org.example.myportfolio.request.ProjectRequest;
import org.example.myportfolio.response.ProjectResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    Project toEntity(ProjectRequest projectRequest);

    ProjectResponse toResponse(Project project);
}
