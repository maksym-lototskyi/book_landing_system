package com.api.book_landing_system.repository;

import com.api.book_landing_system.model.Author;
import com.api.book_landing_system.model.Book;
import com.api.book_landing_system.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByIsbn(String isbn);
    @Query("SELECT b FROM Book b WHERE b.title = :search OR b.author.name = :search OR b.category.name = :search")
    List<Book> findAllByTitleOrAuthorNameOrCategoryName(String search);
}
