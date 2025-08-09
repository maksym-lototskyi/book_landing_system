package com.api.book_landing_system.service_impl;

import com.api.book_landing_system.dtos.loan_dtos.LoanPostDto;
import com.api.book_landing_system.dtos.loan_dtos.LoanPostRespDto;
import com.api.book_landing_system.dtos.loan_dtos.ReturnedBookDto;
import com.api.book_landing_system.mapper.LoanMapper;
import com.api.book_landing_system.model.Book;
import com.api.book_landing_system.model.Fine;
import com.api.book_landing_system.model.Loan;
import com.api.book_landing_system.model.UserEntity;
import com.api.book_landing_system.repository.BookRepository;
import com.api.book_landing_system.repository.FineRepository;
import com.api.book_landing_system.repository.LoanRepository;
import com.api.book_landing_system.repository.UserRepository;
import com.api.book_landing_system.service.LoanService;
import com.api.book_landing_system.verification.Verifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
class LoanServiceImpl implements LoanService {
    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    private final FineRepository fineRepository;



    @Autowired
    public LoanServiceImpl(LoanRepository loanRepository, UserRepository userRepository, BookRepository bookRepository, FineRepository fineRepository) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.fineRepository = fineRepository;
    }

    @Override
    public LoanPostRespDto borrowBook(LoanPostDto loanPostDto) {
        Verifier.verifyExistence(loanPostDto.getUserId(), userRepository);
        Verifier.verifyExistence(loanPostDto.getBookId(), bookRepository);

        UserEntity user = userRepository.findById(loanPostDto.getUserId()).get();
        Book book = bookRepository.findById(loanPostDto.getBookId()).get();

        if(loanRepository.existsByBookAndUser(book, user)){
            throw new IllegalArgumentException("This user already borrowed book named " + book.getTitle());
        }

        LocalDate current = LocalDate.now();

        Loan loan = Loan.builder()
                .book(book)
                .user(user)
                .borrowDate(current)
                .dueDate(current.plusDays(BORROW_TIME_DAYS))
                .build();

        Loan saved = loanRepository.save(loan);
        return LoanMapper.loanToLoanPostRespDto(saved);
    }

    @Override
    public ReturnedBookDto returnBook(Long loanId) {
        Verifier.verifyExistence(loanId, loanRepository);

        Loan loan = loanRepository.findById(loanId).get();

        LocalDate returnDate = LocalDate.now();
        loan.setReturnDate(returnDate);

        ReturnedBookDto returnedBookDto = ReturnedBookDto
                .builder()
                .amount(0.0)
                .message("The book was successfully returned")
                .returnDate(returnDate)
                .build();

        if (returnDate.isAfter(loan.getDueDate())) {
            Fine fine = Fine.builder()
                    .amount(ChronoUnit.DAYS.between(loan.getDueDate(), returnDate) * 1.0)
                    .loan(loan)
                    .isPaid(false)
                    .build();
            fineRepository.save(fine);
            returnedBookDto.setAmount(fine.getAmount());
        }

        loanRepository.save(loan);
        return returnedBookDto;
    }
}
