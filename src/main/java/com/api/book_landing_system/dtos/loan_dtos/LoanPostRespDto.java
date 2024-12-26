package com.api.book_landing_system.dtos.loan_dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class LoanPostRespDto {
    private String message;
    private LocalDate dueDate;

}
