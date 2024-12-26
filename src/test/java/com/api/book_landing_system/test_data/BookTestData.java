package com.api.book_landing_system.test_data;

import com.api.book_landing_system.dtos.book_dtos.BookGetRespDto;
import com.api.book_landing_system.dtos.book_dtos.BookPostDto;
import com.api.book_landing_system.dtos.book_dtos.BookPostRespDto;
import com.api.book_landing_system.model.Author;
import com.api.book_landing_system.model.Book;
import com.api.book_landing_system.model.Category;

import java.util.ArrayList;

public class BookTestData {
    private static final Long ID = 1L;
    private static final String TITLE = "Duma i Uprzedzenie";
    private static final String AUTHOR_NAME = "Jane Ostin";
    private static final String CATEGORY_NAME = "Psychological Romans";
    private static final Integer COPIES_NUMBER = 5;
    private static final String ISBN = "8932742189";
    public static Book getTestBook(){
        return Book.builder()
                .id(ID)
                .title(TITLE)
                .author(new Author(ID, AUTHOR_NAME, new ArrayList<>()))
                .category(new Category(ID, CATEGORY_NAME))
                .availableCopies(0)
                .totalCopies(0)
                .isbn(ISBN)
                .build();
    }
    public static BookPostDto getTestBookPostDto(){
        return BookPostDto.builder()
                .authorId(ID)
                .title(TITLE)
                .categoryId(ID)
                .isbn(ISBN)
                .totalCopies(COPIES_NUMBER)
                .availableCopies(COPIES_NUMBER)
                .build();
    }

    public static BookPostRespDto getBookPostRespDto(){
        return BookPostRespDto.builder()
                .totalCopies(COPIES_NUMBER)
                .title(TITLE)
                .authorName(AUTHOR_NAME)
                .build();
    }

    public static BookGetRespDto getTestBookGetRespDto(){
        return BookGetRespDto.builder()
                .id(ID)
                .author(AUTHOR_NAME)
                .title(TITLE)
                .availableCopies(0)
                .isbn(ISBN)
                .build();
    }

}
