package com.api.book_landing_system.dtos.book_dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookPostRespDto {
    private String title;
    private String authorName;
    private String isbn;
    private Integer totalCopies;
}
