package com.api.book_landing_system.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/*
Fine:

id (Primary Key)
user (One-to-One relationship with User)
loan (One-to-One relationship with Loan)
amount (calculated based on overdue days, e.g., $1/day)
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Fine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Loan loan;
    private Double amount;
    @Builder.Default
    private boolean isPaid = false;
}
