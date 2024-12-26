package com.api.book_landing_system.dtos.category_dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategoryGetDto {
    private Long id;
    private String name;

}
