package com.api.book_landing_system.mapper;

import com.api.book_landing_system.dtos.author_dto.AuthorGetDto;
import com.api.book_landing_system.dtos.author_dto.AuthorPostDto;
import com.api.book_landing_system.model.Author;

import java.util.ArrayList;

public class AuthorMapper {
    public static Author authorPostDtoToAuthor(AuthorPostDto authorPostDto){
        return Author.builder()
                .name(authorPostDto.getName())
                .books(new ArrayList<>())
                .build();
    }

    public static AuthorGetDto authorToAuthorGetDto(Author author){
        return new AuthorGetDto(author.getId(), author.getName());
    }
}
