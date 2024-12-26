package com.api.book_landing_system.dtos.loan_dtos;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoanPostDto {
    @PositiveOrZero
    private Long userId;
    @PositiveOrZero
    private Long bookId;
}
