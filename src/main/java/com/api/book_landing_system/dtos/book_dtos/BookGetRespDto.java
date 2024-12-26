package com.api.book_landing_system.dtos.book_dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookGetRespDto {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Integer availableCopies;
}
