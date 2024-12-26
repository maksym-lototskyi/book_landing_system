package com.api.book_landing_system.service;

import com.api.book_landing_system.dtos.user_dtos.LoginDto;
import com.api.book_landing_system.dtos.user_dtos.RegisterDto;

public interface AuthorizationService {
    void registerUser(RegisterDto registerDto);
    void loginUser(LoginDto loginDto);
}
