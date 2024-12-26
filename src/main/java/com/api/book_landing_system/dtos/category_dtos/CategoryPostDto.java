package com.api.book_landing_system.dtos.category_dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategoryPostDto {
    @NotNull
    @NotBlank
    String name;
}
