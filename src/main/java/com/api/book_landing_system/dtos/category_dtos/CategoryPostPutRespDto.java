package com.api.book_landing_system.dtos.category_dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategoryPostPutRespDto {
    private Long id;
    private String name;
    @Builder.Default
    private String message = "Category was updated successfully";
}
