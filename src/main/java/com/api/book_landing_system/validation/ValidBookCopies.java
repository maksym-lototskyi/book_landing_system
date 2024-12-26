package com.api.book_landing_system.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CopiesValidator.class)
@Documented
public @interface ValidBookCopies {
    String message() default "The number of available copies can not be greater than the number of total copies";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
