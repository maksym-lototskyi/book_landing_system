package com.api.book_landing_system.service_impl;

import com.api.book_landing_system.dtos.author_dto.AuthorGetDto;
import com.api.book_landing_system.dtos.author_dto.AuthorPostDto;
import com.api.book_landing_system.dtos.book_dtos.BookGetRespDto;
import com.api.book_landing_system.mapper.AuthorMapper;
import com.api.book_landing_system.mapper.BookMapper;
import com.api.book_landing_system.model.Author;
import com.api.book_landing_system.repository.AuthorRepository;
import com.api.book_landing_system.service.AuthorService;
import com.api.book_landing_system.verification.Verifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorGetDto createAuthor(AuthorPostDto authorPostDto) {
        Author saved = authorRepository.save(AuthorMapper.authorPostDtoToAuthor(authorPostDto));
        return AuthorMapper.authorToAuthorGetDto(saved);
    }

    @Override
    public AuthorGetDto getAuthorDetails(Long id) {
        Author author = verifyAndGetAuthor(id);
        return AuthorMapper.authorToAuthorGetDto(author);
    }

    @Override
    public AuthorGetDto updateAuthor(Long id, AuthorPostDto authorPostDto) {
        Author author = verifyAndGetAuthor(id);
        author.setName(author.getName());

        Author saved = authorRepository.save(author);
        return AuthorMapper.authorToAuthorGetDto(saved);
    }

    @Override
    public List<BookGetRespDto> getListOfBooks(Long id) {
        Author author = verifyAndGetAuthor(id);
        return author.getBooks().stream().map(BookMapper::bookToBookGetRespDto).collect(Collectors.toList());
    }

    private Author verifyAndGetAuthor(Long id){
        Verifier.verifyExistence(id, authorRepository);

        return authorRepository.findById(id).get();
    }
}
