package com.api.book_landing_system.service_impl;

import com.api.book_landing_system.dtos.book_dtos.BookPostDto;
import com.api.book_landing_system.dtos.book_dtos.BookPostRespDto;
import com.api.book_landing_system.exceptions.IdNotFoundException;
import com.api.book_landing_system.exceptions.ObjectAlreadyExistsException;
import com.api.book_landing_system.model.Book;
import com.api.book_landing_system.repository.AuthorRepository;
import com.api.book_landing_system.repository.BookRepository;
import com.api.book_landing_system.repository.CategoryRepository;
import com.api.book_landing_system.test_data.BookTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    BookServiceImpl bookService;

    @Test
    public void testThatCreateBookThrowsExceptionIfCategoryDoesNotExist(){
        BookPostDto postDto = BookTestData.getTestBookPostDto();

        when(authorRepository.existsById(any(Long.class))).thenReturn(true);
        when(categoryRepository.existsById(any(Long.class))).thenReturn(false);
        assertThrows(IdNotFoundException.class, () -> bookService.createBook(postDto));
    }

    @Test
    public void testThatCreateBookThrowsExceptionIfAuthorDoesNotExist(){
        BookPostDto postDto = BookTestData.getTestBookPostDto();

        when(authorRepository.existsById(any(Long.class))).thenReturn(false);
        assertThrows(IdNotFoundException.class, () -> bookService.createBook(postDto));
    }

    @Test
    public void testThatCreateBookThrowsExceptionIfNumberOfTotalCopiesSmallerThanNumberOfAvailableCopies(){
        BookPostDto postDto = BookTestData.getTestBookPostDto();

        postDto.setTotalCopies(5);
        postDto.setAvailableCopies(6);

        assertThrows(IllegalArgumentException.class, () -> bookService.updateBook(1L, postDto));
    }

    @Test
    public void testThatCreateBookThrowsExceptionIfBookAlreadyExists(){
        BookPostDto postDto = BookTestData.getTestBookPostDto();

        when(authorRepository.existsById(any(Long.class))).thenReturn(true);
        when(categoryRepository.existsById(any(Long.class))).thenReturn(true);
        when(bookRepository.existsByIsbn(postDto.getIsbn())).thenReturn(true);

        assertThrows(ObjectAlreadyExistsException.class, () -> bookService.createBook(postDto));

        verify(bookRepository, times(1)).existsByIsbn(postDto.getIsbn());
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    public void testThatCreateBookSavesBook(){
        BookPostDto postDto = BookTestData.getTestBookPostDto();
        Book book = BookTestData.getTestBook();

        when(authorRepository.existsById(any(Long.class))).thenReturn(true);
        when(categoryRepository.existsById(any(Long.class))).thenReturn(true);
        when(bookRepository.existsByIsbn(any())).thenReturn(false);
        when(authorRepository.findById(any())).thenReturn(Optional.of(book.getAuthor()));
        when(categoryRepository.findById(any())).thenReturn(Optional.of(book.getCategory()));
        when(bookRepository.save(any())).thenReturn(book);

        BookPostRespDto saved = bookService.createBook(postDto);

        assertEquals(saved.getIsbn(), book.getIsbn());
    }

    @Test
    public void testThatUpdateBookThrowsExceptionIfBookWithProvidedIdDoesNotExist(){
        BookPostDto postDto = BookTestData.getTestBookPostDto();

        when(bookRepository.existsById(any())).thenReturn(false);

        assertThrows(IdNotFoundException.class, () -> bookService.updateBook(1L, postDto));
        verify(bookRepository, times(1)).existsById(1L);
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    public void testThatUpdateBookThrowsExceptionIfNumberOfTotalCopiesSmallerThanNumberOfAvailableCopies(){
        BookPostDto postDto = BookTestData.getTestBookPostDto();

        postDto.setTotalCopies(5);
        postDto.setAvailableCopies(6);

        assertThrows(IllegalArgumentException.class, () -> bookService.updateBook(1L, postDto));
    }

    @Test
    public void testThatUpdateBookUpdatesBook(){
        BookPostDto postDto = BookTestData.getTestBookPostDto();
        postDto.setTitle("Title");
        Book book = BookTestData.getTestBook();
        book.setTitle("Different title");

        when(bookRepository.existsById(any())).thenReturn(true);
        when(authorRepository.existsById(any(Long.class))).thenReturn(true);
        when(categoryRepository.existsById(any(Long.class))).thenReturn(true);
        when(bookRepository.findById(any())).thenReturn(Optional.of(book));
        when(authorRepository.findById(any())).thenReturn(Optional.of(book.getAuthor()));
        when(categoryRepository.findById(any())).thenReturn(Optional.of(book.getCategory()));
        when(bookRepository.save(book)).thenReturn(book);

        BookPostRespDto result = bookService.updateBook(1L, postDto);

        assertEquals(postDto.getTitle(), result.getTitle());
        assertEquals(postDto.getTitle(), book.getTitle());
    }

    @Test
    public void testThatUpdateBookThrowsExceptionIfCategoryDoesNotExist(){
        BookPostDto postDto = BookTestData.getTestBookPostDto();
        Book book = BookTestData.getTestBook();

        when(bookRepository.existsById(any(Long.class))).thenReturn(true);
        when(bookRepository.findById(any())).thenReturn(Optional.of(book));
        when(authorRepository.existsById(any(Long.class))).thenReturn(true);
        when(categoryRepository.existsById(any(Long.class))).thenReturn(false);

        assertThrows(IdNotFoundException.class, () -> bookService.updateBook(1L, postDto));
    }

    @Test
    public void testThatUpdateBookThrowsExceptionIfAuthorDoesNotExist(){
        BookPostDto postDto = BookTestData.getTestBookPostDto();
        Book book = BookTestData.getTestBook();

        when(bookRepository.existsById(any())).thenReturn(true);
        when(bookRepository.findById(any())).thenReturn(Optional.of(book));
        when(authorRepository.existsById(any(Long.class))).thenReturn(false);

        assertThrows(IdNotFoundException.class, () -> bookService.updateBook(1L, postDto));
    }

    @Test
    public void testThatDeleteBookThrowsExceptionIfBookWithProvidedIdDoesNotExist(){
        BookPostDto postDto = BookTestData.getTestBookPostDto();

        when(bookRepository.existsById(any())).thenReturn(false);

        assertThrows(IdNotFoundException.class, () -> bookService.updateBook(1L, postDto));
        verify(bookRepository, times(1)).existsById(1L);
        verifyNoMoreInteractions(bookRepository);
    }

}