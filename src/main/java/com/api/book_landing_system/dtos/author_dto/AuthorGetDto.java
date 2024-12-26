package com.api.book_landing_system.dtos.author_dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthorGetDto {
    private Long id;
    private String name;
}
