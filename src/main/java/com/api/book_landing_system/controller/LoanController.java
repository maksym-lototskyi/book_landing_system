package com.api.book_landing_system.controller;

import com.api.book_landing_system.dtos.loan_dtos.LoanPostDto;
import com.api.book_landing_system.dtos.loan_dtos.LoanPostRespDto;
import com.api.book_landing_system.dtos.loan_dtos.ReturnedBookDto;
import com.api.book_landing_system.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loans")
public class LoanController {
    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    public ResponseEntity<LoanPostRespDto> borrowBook(@Valid @RequestBody LoanPostDto loanPostDto){
        LoanPostRespDto result = loanService.borrowBook(loanPostDto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<ReturnedBookDto> returnBook(@PathVariable Long id){
        ReturnedBookDto result = loanService.returnBook(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
