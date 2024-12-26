package com.api.book_landing_system.service;

import com.api.book_landing_system.dtos.author_dto.AuthorGetDto;
import com.api.book_landing_system.dtos.author_dto.AuthorPostDto;
import com.api.book_landing_system.dtos.book_dtos.BookGetRespDto;

import java.util.List;

public interface AuthorService {
    AuthorGetDto createAuthor(AuthorPostDto authorPostDto);
    AuthorGetDto getAuthorDetails(Long id);
    AuthorGetDto updateAuthor(Long id, AuthorPostDto authorPostDto);
    List<BookGetRespDto> getListOfBooks(Long id);
}
