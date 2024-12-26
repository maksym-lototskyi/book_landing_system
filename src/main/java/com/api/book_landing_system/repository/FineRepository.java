package com.api.book_landing_system.repository;

import com.api.book_landing_system.model.Fine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FineRepository extends JpaRepository<Fine, Long> {
    @Query("SELECT f FROM Fine f WHERE f.loan.user.id = :userId AND f.isPaid = false ")
    List<Fine> findAllUnpaidByUserId(Long userId);

    @Query("SELECT f FROM Fine f WHERE f.loan.user = :userId")
    List<Fine> findAllByUserId(Long userId);
}
