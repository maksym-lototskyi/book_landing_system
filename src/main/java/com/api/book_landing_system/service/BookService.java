package com.api.book_landing_system.service;

import com.api.book_landing_system.dtos.book_dtos.BookGetRespDto;
import com.api.book_landing_system.dtos.book_dtos.BookPostDto;
import com.api.book_landing_system.dtos.book_dtos.BookPostRespDto;

import java.util.List;

public interface BookService {
    BookPostRespDto createBook(BookPostDto postDto);
    BookPostRespDto updateBook(Long bookId, BookPostDto postDto);
    void deleteBook(Long bookId);
    List<BookGetRespDto> getAllBooks();
    List<BookGetRespDto> getBooksBySearch(String search);
}
