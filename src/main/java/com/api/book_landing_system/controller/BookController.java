package com.api.book_landing_system.controller;

import com.api.book_landing_system.dtos.book_dtos.BookGetRespDto;
import com.api.book_landing_system.dtos.book_dtos.BookPostDto;
import com.api.book_landing_system.dtos.book_dtos.BookPostRespDto;
import com.api.book_landing_system.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<BookPostRespDto> addBook(@Valid @RequestBody BookPostDto bookPostDto){
        BookPostRespDto response = bookService.createBook(bookPostDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<BookPostRespDto> updateBook(@Valid @RequestBody BookPostDto bookPostDto, @PathVariable Long bookId){
        BookPostRespDto response = bookService.updateBook(bookId, bookPostDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<String> deleteBook(@PathVariable Long bookId){
        bookService.deleteBook(bookId);
        return new ResponseEntity<>("The book has been deleted successfully", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<BookGetRespDto>> retrieveAllBooks(){
        List<BookGetRespDto> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookGetRespDto>> retrieveBooksBySearch(@RequestBody String search){
        List<BookGetRespDto> foundBooks = bookService.getBooksBySearch(search);
        return new ResponseEntity<>(foundBooks, HttpStatus.OK);
    }
}
