package com.api.book_landing_system.mapper;

import com.api.book_landing_system.dtos.loan_dtos.LoanGetDto;
import com.api.book_landing_system.dtos.loan_dtos.LoanPostRespDto;
import com.api.book_landing_system.model.Loan;

public class LoanMapper {
    public static LoanPostRespDto loanToLoanPostRespDto(Loan loan){
        return LoanPostRespDto.builder()
                .message("The book was successfully borrowed")
                .dueDate(loan.getDueDate())
                .build();
    }
    public static LoanGetDto loanToLoanGetDto(Loan loan){
        return LoanGetDto.builder()
                .loanId(loan.getId())
                .bookTitle(loan.getBook().getTitle())
                .borrowedDate(loan.getBorrowDate())
                .dueDate(loan.getDueDate())
                .returnDate(loan.getReturnDate())
                .build();
    }
}
