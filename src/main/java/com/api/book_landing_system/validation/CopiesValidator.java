package com.api.book_landing_system.validation;

import com.api.book_landing_system.dtos.book_dtos.BookPostDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CopiesValidator implements ConstraintValidator<ValidBookCopies, BookPostDto> {
    @Override
    public boolean isValid(BookPostDto postDto, ConstraintValidatorContext constraintValidatorContext) {
        return postDto.getTotalCopies() >= postDto.getAvailableCopies();
    }
}
