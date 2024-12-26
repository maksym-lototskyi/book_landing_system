package com.api.book_landing_system.model;

import jakarta.persistence.*;
import lombok.*;

//Book:
//
//id (Primary Key)
//title
//author
//isbn (unique)
//availableCopies
//totalCopies
//category (Many-to-One relationship with Category)
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(unique = true)
    private String isbn;
    private Integer availableCopies;
    private Integer totalCopies;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Category category;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Author author;
}
