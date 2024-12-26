package com.api.book_landing_system.controller;

import com.api.book_landing_system.dtos.fine_dtos.FineGetDto;
import com.api.book_landing_system.dtos.loan_dtos.LoanGetDto;
import com.api.book_landing_system.dtos.user_dtos.UserGetDto;
import com.api.book_landing_system.service.FineService;
import com.api.book_landing_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final FineService fineService;

    @Autowired
    public UserController(UserService userService, FineService fineService) {
        this.userService = userService;
        this.fineService = fineService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserGetDto> getUserInfo(@PathVariable Long id){
        UserGetDto result = userService.getUserInfo(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}/loans")
    public ResponseEntity<List<LoanGetDto>> getUserLoans(@PathVariable Long id){
        List<LoanGetDto> loans = userService.getUserLoans(id);
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }

    @PostMapping("/{userId}/fine/{fineId}/pay")
    public ResponseEntity<String> payFine(@PathVariable Long userId, @PathVariable  Long fineId){
        fineService.payFines(userId, fineId);
        return new ResponseEntity<>("The fine was paid successfully", HttpStatus.OK);
    }

    @GetMapping("/{id}/fines")
    public ResponseEntity<List<FineGetDto>> getUserFines(@PathVariable Long id){
        List<FineGetDto> userFines = fineService.getUserFines(id);
        return new ResponseEntity<>(userFines, HttpStatus.OK);
    }
}
