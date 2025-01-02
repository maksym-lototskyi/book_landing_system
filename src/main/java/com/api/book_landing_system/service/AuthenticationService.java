package com.api.book_landing_system.service;

import com.api.book_landing_system.dtos.user_dtos.RegisterDto;

public interface AuthenticationService {
    void registerUser(RegisterDto registerDto);
}
