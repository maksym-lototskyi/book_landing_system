package com.api.book_landing_system.dtos.fine_dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FineGetDto {
    private Long id;
    private double amount;
    private boolean isPaid;
}
