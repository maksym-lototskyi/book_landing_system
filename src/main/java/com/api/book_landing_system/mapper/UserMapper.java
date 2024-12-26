package com.api.book_landing_system.mapper;

import com.api.book_landing_system.dtos.user_dtos.LoginDto;
import com.api.book_landing_system.dtos.user_dtos.UserGetDto;
import com.api.book_landing_system.model.Role;
import com.api.book_landing_system.model.UserEntity;

import java.util.stream.Collectors;

public class UserMapper {
    public static UserGetDto userEntityToUserGetDto(UserEntity user){
        return UserGetDto.builder()
                .id(user.getId())
                .roles(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .username(user.getUsername())
                .build();
    }
}
