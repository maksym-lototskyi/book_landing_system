package com.api.book_landing_system.dtos.user_dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class LoginDto {
    private String username;
    private String password;
}
