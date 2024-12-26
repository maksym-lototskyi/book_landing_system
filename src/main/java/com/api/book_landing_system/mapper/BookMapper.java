package com.api.book_landing_system.mapper;

import com.api.book_landing_system.dtos.book_dtos.BookGetRespDto;
import com.api.book_landing_system.dtos.book_dtos.BookPostDto;
import com.api.book_landing_system.dtos.book_dtos.BookPostRespDto;
import com.api.book_landing_system.model.Author;
import com.api.book_landing_system.model.Book;
import com.api.book_landing_system.model.Category;

public class BookMapper {
    public static Book bookPostDtoToBook(BookPostDto postDto, Author author, Category category){
        return Book.builder()
                .title(postDto.getTitle())
                .totalCopies(postDto.getTotalCopies())
                .availableCopies(postDto.getAvailableCopies())
                .category(category)
                .isbn(postDto.getIsbn())
                .author(author)
                .build();
    }

    public static BookPostRespDto bookToBookPostRespDto(Book book){
        return BookPostRespDto.builder()
                .authorName(book.getAuthor().getName())
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .totalCopies(book.getTotalCopies())
                .build();
    }

    public static BookGetRespDto bookToBookGetRespDto(Book book){
        return BookGetRespDto.builder()
                .id(book.getId())
                .author(book.getAuthor().getName())
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .availableCopies(book.getAvailableCopies())
                .build();
    }
}
