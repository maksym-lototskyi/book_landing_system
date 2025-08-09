package com.api.book_landing_system.service_impl;

import com.api.book_landing_system.dtos.book_dtos.BookGetRespDto;
import com.api.book_landing_system.dtos.book_dtos.BookPostDto;
import com.api.book_landing_system.dtos.book_dtos.BookPostRespDto;
import com.api.book_landing_system.exceptions.ObjectAlreadyExistsException;
import com.api.book_landing_system.mapper.BookMapper;
import com.api.book_landing_system.model.Author;
import com.api.book_landing_system.model.Book;
import com.api.book_landing_system.model.Category;
import com.api.book_landing_system.repository.AuthorRepository;
import com.api.book_landing_system.repository.BookRepository;
import com.api.book_landing_system.repository.CategoryRepository;
import com.api.book_landing_system.service.BookService;
import com.api.book_landing_system.verification.Verifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public BookPostRespDto createBook(BookPostDto postDto) {
        verifyAvailableAndTotalCopies(postDto);
        Verifier.verifyExistence(postDto.getAuthorId(), authorRepository);
        Verifier.verifyExistence(postDto.getCategoryId(), categoryRepository);

        if(bookRepository.existsByIsbn(postDto.getIsbn())){
            throw new ObjectAlreadyExistsException("Book with that ISBN already exists");
        }


        Optional<Author> author = authorRepository.findById(postDto.getAuthorId());
        Optional<Category> category = categoryRepository.findById(postDto.getCategoryId());

        Book saved = bookRepository.save(BookMapper.bookPostDtoToBook(postDto, author.get(), category.get()));
        return BookMapper.bookToBookPostRespDto(saved);
    }

    @Override
    public BookPostRespDto updateBook(Long bookId, BookPostDto postDto) {
        verifyAvailableAndTotalCopies(postDto);
        Verifier.verifyExistence(bookId, bookRepository);

        Book bookToUpdate = bookRepository.findById(bookId).get();

        Verifier.verifyExistence(postDto.getAuthorId(), authorRepository);
        Verifier.verifyExistence(postDto.getCategoryId(), categoryRepository);

        Author author = authorRepository.findById(postDto.getAuthorId()).get();
        Category category = categoryRepository.findById(postDto.getCategoryId()).get();

        bookToUpdate.setAuthor(author);
        bookToUpdate.setCategory(category);
        bookToUpdate.setIsbn(postDto.getIsbn());
        bookToUpdate.setTitle(postDto.getTitle());
        bookToUpdate.setTotalCopies(postDto.getTotalCopies());
        bookToUpdate.setAvailableCopies(postDto.getAvailableCopies());

        Book updated = bookRepository.save(bookToUpdate);

        return BookMapper.bookToBookPostRespDto(updated);
    }

    private void verifyAvailableAndTotalCopies(BookPostDto postDto){
        if(postDto.getAvailableCopies() > postDto.getTotalCopies()){
            throw new IllegalArgumentException("The number of available copies can not be greater than the number of total copies");
        }
    }

    @Override
    public void deleteBook(Long bookId) {
        if(!bookRepository.existsById(bookId)){
            throw new IllegalArgumentException("There is no book with id: " + bookId);
        }
        bookRepository.deleteById(bookId);
    }

    @Override
    public List<BookGetRespDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookMapper::bookToBookGetRespDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookGetRespDto> getBooksBySearch(String search) {
        return bookRepository.findAllByTitleOrAuthorNameOrCategoryName(search)
                .stream()
                .map(BookMapper::bookToBookGetRespDto)
                .collect(Collectors.toList());
    }
}
