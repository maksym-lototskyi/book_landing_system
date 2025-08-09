package com.api.book_landing_system.service_impl;

import com.api.book_landing_system.dtos.loan_dtos.LoanGetDto;
import com.api.book_landing_system.dtos.user_dtos.UserGetDto;
import com.api.book_landing_system.mapper.LoanMapper;
import com.api.book_landing_system.mapper.UserMapper;
import com.api.book_landing_system.model.UserEntity;
import com.api.book_landing_system.repository.LoanRepository;
import com.api.book_landing_system.repository.UserRepository;
import com.api.book_landing_system.service.UserService;
import com.api.book_landing_system.verification.Verifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final LoanRepository loanRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, LoanRepository loanRepository) {
        this.userRepository = userRepository;
        this.loanRepository = loanRepository;
    }

    @Override
    public UserGetDto getUserInfo(Long userId) {
        Verifier.verifyExistence(userId, userRepository);
        UserEntity user = userRepository.findById(userId).get();
        return UserMapper.userEntityToUserGetDto(user);
    }

    @Override
    public List<LoanGetDto> getUserLoans(Long userId) {
        Verifier.verifyExistence(userId, userRepository);
        UserEntity user = userRepository.findById(userId).get();
        return loanRepository.findByUser(user).stream().map(LoanMapper::loanToLoanGetDto).collect(Collectors.toList());
    }
}
