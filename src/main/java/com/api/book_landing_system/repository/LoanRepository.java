package com.api.book_landing_system.repository;

import com.api.book_landing_system.model.Book;
import com.api.book_landing_system.model.Loan;
import com.api.book_landing_system.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByUser(UserEntity user);
    boolean existsByBookAndUser(Book book, UserEntity user);
}
