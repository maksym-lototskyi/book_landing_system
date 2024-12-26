package com.api.book_landing_system.dtos.book_dtos;

import com.api.book_landing_system.validation.ValidBookCopies;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@ValidBookCopies
public class BookPostDto {
    @NotBlank
    private String title;
    @NotBlank
    private String isbn;
    @NotNull
    private Long categoryId;
    @NotNull
    private Long authorId;
    @PositiveOrZero
    private Integer totalCopies;
    @PositiveOrZero
    private Integer availableCopies;
}
