package com.api.book_landing_system.dtos.loan_dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ReturnedBookDto {
    private String message;
    private Double amount;
    private LocalDate returnDate;
}
