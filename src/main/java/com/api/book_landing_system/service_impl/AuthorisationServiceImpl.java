package com.api.book_landing_system.service_impl;

import com.api.book_landing_system.dtos.user_dtos.LoginDto;
import com.api.book_landing_system.dtos.user_dtos.RegisterDto;
import com.api.book_landing_system.exceptions.ObjectAlreadyExistsException;
import com.api.book_landing_system.model.Role;
import com.api.book_landing_system.model.UserEntity;
import com.api.book_landing_system.repository.RoleRepository;
import com.api.book_landing_system.repository.UserRepository;
import com.api.book_landing_system.service.AuthorizationService;
import com.api.book_landing_system.verification.Verifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorisationServiceImpl implements AuthorizationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public AuthorisationServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    @Override
    public void registerUser(RegisterDto registerDto) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(registerDto.getUsername());
        if(userEntity.isEmpty()){
            List<Role> roles = new ArrayList<>();
            for (Long roleId : registerDto.getRoleIds()){
                Verifier.verifyExistence(roleId, roleRepository);
                Role role = roleRepository.findById(roleId).get();
                roles.add(role);
            }
            userRepository.save(UserEntity.builder()
                    .roles(roles)
                    .password(encoder.encode(registerDto.getPassword()))
                    .username(registerDto.getUsername())
                    .loans(new ArrayList<>())
                    .build());
        }
        else{
            throw new ObjectAlreadyExistsException("The user with such username already exists");
        }
    }

    @Override
    public void loginUser(LoginDto loginDto) {

    }
}
