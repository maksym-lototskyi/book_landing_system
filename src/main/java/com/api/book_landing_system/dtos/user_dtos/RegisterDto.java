package com.api.book_landing_system.dtos.user_dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class RegisterDto {
    private String username;
    private String password;
    private List<Long> roleIds;
}