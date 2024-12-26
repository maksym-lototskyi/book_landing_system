package com.api.book_landing_system.service;

import com.api.book_landing_system.dtos.loan_dtos.LoanGetDto;
import com.api.book_landing_system.dtos.user_dtos.UserGetDto;

import java.util.List;

public interface UserService {
    UserGetDto getUserInfo(Long userId);
    List<LoanGetDto> getUserLoans(Long userId);
}
