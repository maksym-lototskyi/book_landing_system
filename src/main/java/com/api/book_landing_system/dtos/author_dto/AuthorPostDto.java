package com.api.book_landing_system.dtos.author_dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthorPostDto {
    @NotBlank
    private String name;
}
