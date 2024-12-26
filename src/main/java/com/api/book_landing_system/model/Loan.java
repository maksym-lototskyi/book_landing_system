package com.api.book_landing_system.model;

/*
Loan:

id (Primary Key)
user (Many-to-One relationship with User)
book (Many-to-One relationship with Book)
borrowDate
dueDate
returnDate (nullable, when the book is returned)
Derived Field: isOverdue (calculated from dueDate and returnDate)
 */

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @ManyToOne
    private Book book;
    @CreationTimestamp
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
}
