package com.api.book_landing_system.mapper;

import com.api.book_landing_system.dtos.fine_dtos.FineGetDto;
import com.api.book_landing_system.model.Fine;

public class FineMapper {
    public static FineGetDto fineToFineGetDto(Fine fine){
        return FineGetDto.
                builder()
                .id(fine.getId())
                .amount(fine.getAmount())
                .isPaid(fine.isPaid())
                .build();
    }
}
