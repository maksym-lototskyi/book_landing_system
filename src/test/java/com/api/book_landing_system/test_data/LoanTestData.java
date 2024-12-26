package com.api.book_landing_system.test_data;

import com.api.book_landing_system.dtos.loan_dtos.LoanPostDto;
import com.api.book_landing_system.model.Loan;

import java.time.LocalDate;

public class LoanTestData {
    private static final Long ID = 1L;

    public static LoanPostDto getTestLoanPostDto(){
        return LoanPostDto.builder()
                .bookId(ID)
                .userId(ID)
                .build();
    }

    public static Loan getTestLoan(){
        return Loan.builder()
                .user(UserTestData.getTestUser())
                .book(BookTestData.getTestBook())
                .borrowDate(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(14))
                .build();
    }
}
