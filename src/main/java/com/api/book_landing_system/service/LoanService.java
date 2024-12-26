package com.api.book_landing_system.service;

import com.api.book_landing_system.dtos.loan_dtos.LoanGetDto;
import com.api.book_landing_system.dtos.loan_dtos.LoanPostDto;
import com.api.book_landing_system.dtos.loan_dtos.LoanPostRespDto;
import com.api.book_landing_system.dtos.loan_dtos.ReturnedBookDto;

import java.util.List;

public interface LoanService {
    int BORROW_TIME_DAYS = 14;

    LoanPostRespDto borrowBook(LoanPostDto loanPostDto);
    ReturnedBookDto returnBook(Long id);
}
