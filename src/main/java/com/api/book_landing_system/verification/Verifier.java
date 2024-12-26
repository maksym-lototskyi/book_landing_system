package com.api.book_landing_system.verification;

import com.api.book_landing_system.exceptions.IdNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public class Verifier {
    public static <T> void verifyExistence(Long id, JpaRepository<T, Long> repository) {
        if (!repository.existsById(id)) {
            throw new IdNotFoundException("There is no object with id: " + id + " in the repository");
        }
    }
}
