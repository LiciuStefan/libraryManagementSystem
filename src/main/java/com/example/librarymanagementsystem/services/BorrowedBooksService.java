package com.example.librarymanagementsystem.services;


import com.example.librarymanagementsystem.models.Book;
import com.example.librarymanagementsystem.models.BorrowedBooks;
import com.example.librarymanagementsystem.repositories.BookRepository;
import com.example.librarymanagementsystem.repositories.BorrowedBooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.librarymanagementsystem.dtos.BookUpdateDto;

import java.util.List;

@Service
public class BorrowedBooksService {
    private final BorrowedBooksRepository borrowedBooksRepository;
    private final BookRepository bookRepository;
    private final BookService bookService;

    @Autowired
    public BorrowedBooksService(BorrowedBooksRepository borrowedBooksRepository, BookRepository bookRepository, BookService bookService) {
        this.borrowedBooksRepository = borrowedBooksRepository;
        this.bookRepository = bookRepository;
        this.bookService = bookService;
    }

    public void borrowBook(Long bookId, Long userId)
    {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Book with id " + bookId + " does not exist"));
        if(book.getAvailableNumberOfCopies() == 0)
            throw new IllegalArgumentException("Book with id " + bookId + " is not available");
        bookService.updateBook(bookId, BookUpdateDto.builder().availableNumberOfCopies(book.getAvailableNumberOfCopies() - 1).build());

        BorrowedBooks borrowedBooks = BorrowedBooks.builder()
                .book(book)
                .userId(userId)
                .status("BORROWED")
                .build();

        borrowedBooksRepository.save(borrowedBooks);
    }

    public void returnBook(Long bookId, Long userId)
    {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Book with id " + bookId + " does not exist"));
        List<BorrowedBooks> borrowedBooks = borrowedBooksRepository.findAllById(bookId);
        for(BorrowedBooks borrowedBook : borrowedBooks)
        {
            System.out.println(borrowedBook.getUserId());
            if(borrowedBook.getUserId().equals(userId))
            {
                borrowedBook.setStatus("RETURNED");
                borrowedBooksRepository.save(borrowedBook);
                bookService.updateBook(bookId, BookUpdateDto.builder().availableNumberOfCopies(book.getAvailableNumberOfCopies() + 1).build());
            }
        }
    }

}
