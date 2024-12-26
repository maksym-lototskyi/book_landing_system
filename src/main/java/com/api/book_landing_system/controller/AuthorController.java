package com.api.book_landing_system.controller;

import com.api.book_landing_system.dtos.author_dto.AuthorGetDto;
import com.api.book_landing_system.dtos.author_dto.AuthorPostDto;
import com.api.book_landing_system.dtos.book_dtos.BookGetRespDto;
import com.api.book_landing_system.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<AuthorGetDto> createAuthor(@Valid @RequestBody AuthorPostDto authorPostDto){
        AuthorGetDto response = authorService.createAuthor(authorPostDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorGetDto> getAuthorDetails(@PathVariable Long id){
        AuthorGetDto response = authorService.getAuthorDetails(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}/books")
    public ResponseEntity<List<BookGetRespDto>> getAuthorBooks(@PathVariable Long id){
        List<BookGetRespDto> response = authorService.getListOfBooks(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorGetDto> updateAuthor(@PathVariable Long id, @Valid @RequestBody AuthorPostDto authorPostDto){
        AuthorGetDto response = authorService.updateAuthor(id, authorPostDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
