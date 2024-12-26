package com.api.book_landing_system.service_impl;

import com.api.book_landing_system.dtos.loan_dtos.LoanPostDto;
import com.api.book_landing_system.dtos.loan_dtos.LoanPostRespDto;
import com.api.book_landing_system.dtos.loan_dtos.ReturnedBookDto;
import com.api.book_landing_system.exceptions.IdNotFoundException;
import com.api.book_landing_system.model.Book;
import com.api.book_landing_system.model.Fine;
import com.api.book_landing_system.model.Loan;
import com.api.book_landing_system.model.UserEntity;
import com.api.book_landing_system.repository.BookRepository;
import com.api.book_landing_system.repository.FineRepository;
import com.api.book_landing_system.repository.LoanRepository;
import com.api.book_landing_system.repository.UserRepository;
import com.api.book_landing_system.test_data.BookTestData;
import com.api.book_landing_system.test_data.LoanTestData;
import com.api.book_landing_system.test_data.UserTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanServiceImplTest {
    @Mock
    private LoanRepository loanRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private FineRepository fineRepository;
    @InjectMocks
    LoanServiceImpl loanService;
    private LoanPostDto postDto;

    @BeforeEach
    public void setUp(){
        postDto = LoanTestData.getTestLoanPostDto();
    }
    @Test
    public void testThatBorrowBookThrowsExceptionIfUserDoesNotExist(){
        when(userRepository.existsById(any())).thenThrow(new IllegalArgumentException());

        assertThrows(IllegalArgumentException.class, () -> loanService.borrowBook(postDto));
        verify(userRepository, times(1)).existsById(any());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testThatBorrowedBookThrowsExceptionIfBookDoesNotExist(){
        when(userRepository.existsById(any())).thenReturn(true);
        when(bookRepository.existsById(any())).thenReturn(false);

        assertThrows(IdNotFoundException.class, () -> loanService.borrowBook(postDto));
        verify(bookRepository, times(1)).existsById(any());
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    public void testThatBorrowBookThrowsExceptionIfUserAlreadyBorrowedThatBook(){
        UserEntity user = UserTestData.getTestUser();
        Book book = BookTestData.getTestBook();
        when(userRepository.existsById(any(Long.class))).thenReturn(true);
        when(bookRepository.existsById(any(Long.class))).thenReturn(true);
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));

        when(loanRepository.existsByBookAndUser(book, user)).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> loanService.borrowBook(postDto));

        verify(userRepository, times(1)).existsById(any());
        verify(bookRepository, times(1)).existsById(any());
        verify(userRepository, times(1)).findById(any());
        verify(bookRepository, times(1)).findById(any());
        verify(loanRepository, times(1)).existsByBookAndUser(book, user);
        verifyNoMoreInteractions(bookRepository);
        verifyNoMoreInteractions(loanRepository);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testBorrowBookSuccess(){
        Loan loan = LoanTestData.getTestLoan();

        when(userRepository.existsById(any(Long.class))).thenReturn(true);
        when(bookRepository.existsById(any(Long.class))).thenReturn(true);
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(new UserEntity()));
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(BookTestData.getTestBook()));
        when(loanRepository.existsByBookAndUser(any(Book.class), any(UserEntity.class))).thenReturn(false);
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);

        LoanPostRespDto result = loanService.borrowBook(postDto);

        assertNotNull(result);
        assertEquals(result.getDueDate(), loan.getDueDate());

        verify(userRepository, times(1)).existsById(any());
        verify(bookRepository, times(1)).existsById(any());
        verify(userRepository, times(1)).findById(any());
        verify(bookRepository, times(1)).findById(any());
        verifyNoMoreInteractions(bookRepository);
        verifyNoMoreInteractions(userRepository);
        verify(loanRepository, times(1)).save(any());
    }

    @Test
    public void testThatReturnBookThrowsExceptionIfLoanNotExists(){
        when(loanRepository.existsById(any(Long.class))).thenReturn(false);

        assertThrows(IdNotFoundException.class, () -> loanService.returnBook(any(Long.class)));
        verify(loanRepository, times(1)).existsById(any(Long.class));
        verifyNoMoreInteractions(loanRepository);
        verifyNoInteractions(fineRepository);
        verifyNoInteractions(userRepository);
        verifyNoInteractions(bookRepository);
    }

    @Test
    public void testTestFineWasCreatedIfReturnDateMoreThanDueDate(){
        Loan loan = LoanTestData.getTestLoan();
        loan.setDueDate(LocalDate.of(2024, Month.DECEMBER, 22));

        when(loanRepository.existsById(any(Long.class))).thenReturn(true);
        when(loanRepository.findById(any(Long.class))).thenReturn(Optional.of(loan));

        ReturnedBookDto returnedBookDto = loanService.returnBook(1L);
        Double expectedAmount = ChronoUnit.DAYS.between(loan.getDueDate(), loan.getReturnDate()) * 1.0;

        assertEquals(expectedAmount, returnedBookDto.getAmount());

        verify(fineRepository, times(1)).save(any(Fine.class));
        verify(loanRepository, times(1)).save(any(Loan.class));
        verify(loanRepository, times(1)).findById(any(Long.class));

        verifyNoMoreInteractions(loanRepository);
        verifyNoMoreInteractions(fineRepository);

    }

    @Test
    public void testThatReturnBookDoesNotCreateFineWhenBookReturnedOnTime(){
        Loan loan = LoanTestData.getTestLoan();
        loan.setDueDate(LocalDate.now().plusDays(5));

        when(loanRepository.existsById(any(Long.class))).thenReturn(true);
        when(loanRepository.findById(any(Long.class))).thenReturn(Optional.of(loan));

        ReturnedBookDto returnedBookDto = loanService.returnBook(1L);
        Double expectedAmount = 0.0;

        assertEquals(expectedAmount, returnedBookDto.getAmount());

        verify(loanRepository, times(1)).save(any(Loan.class));
        verify(loanRepository, times(1)).findById(any(Long.class));

        verifyNoMoreInteractions(loanRepository);
        verifyNoInteractions(fineRepository);
    }

    /*@Test
    public void testThatGetUserLoansThrowsExceptionIfUserDoesNotExist(){
        when(userRepository.existsById(any(Long.class))).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> loanService.getUserLoans(1L));
        verify(userRepository).existsById(any(Long.class));
        verifyNoMoreInteractions(userRepository);
    }*/

}