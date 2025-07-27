package org.example.myportfolio.mapper;

import org.example.myportfolio.dao.entity.User;
import org.example.myportfolio.request.UserRequest;
import org.example.myportfolio.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {


    @Mapping(target = "id", ignore = true)
    User toEntity(UserRequest userRequest);

    //    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", source = "role")
    UserResponse toResponse(User user);

    List<UserResponse> toResponseList(List<User> users);
}

