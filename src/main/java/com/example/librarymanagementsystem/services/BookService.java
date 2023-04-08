package com.example.librarymanagementsystem.services;


import com.example.librarymanagementsystem.dtos.BookUpdateDto;
import com.example.librarymanagementsystem.dtos.NewBookDto;
import com.example.librarymanagementsystem.models.Author;
import com.example.librarymanagementsystem.models.Book;
import com.example.librarymanagementsystem.repositories.AuthorRepository;
import com.example.librarymanagementsystem.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public void addBook(NewBookDto newBookDto)
    {
        if(newBookDto.getAvailableNumberOfCopies() < 0 || newBookDto.getTotalNumberOfCopies() < 0)
            throw new IllegalArgumentException("Number of copies cannot be negative");
        if(newBookDto.getYear() < 0)
            throw new IllegalArgumentException("Year cannot be negative");

        Author author = authorRepository.findById(newBookDto.getAuthorId()).orElseThrow(() -> new IllegalArgumentException("Author with id " + newBookDto.getAuthorId() + " does not exist"));
        Optional<Book> booksWithSameNameAndAuthor = bookRepository.findByTitleAndAuthorId(newBookDto.getTitle(), newBookDto.getAuthorId());

        if(booksWithSameNameAndAuthor.isPresent())
            throw new IllegalArgumentException("Book with title " + newBookDto.getTitle() + " and author " + author.getFirstName() + " " + author.getLastName() + " already exists");

        Book book = Book.builder()
                .title(newBookDto.getTitle())
                .year(newBookDto.getYear())
                .totalNumberOfCopies(newBookDto.getTotalNumberOfCopies())
                .availableNumberOfCopies(newBookDto.getAvailableNumberOfCopies())
                .author(author)
                .category(newBookDto.getCategory())
                .build();
        bookRepository.save(book);
    }

    //TODO: delete book records from book_loan table
    public void deleteBook(Long id)
    {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Book with id " + id + " does not exist"));
        bookRepository.delete(book);
    }

    public void updateBook(Long id, BookUpdateDto bookUpdateDto)
    {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Book with id " + id + " does not exist"));

        if(bookUpdateDto.getAvailableNumberOfCopies() != null)
        {
            if(bookUpdateDto.getAvailableNumberOfCopies() < 0)
                throw new IllegalArgumentException("Number of copies cannot be negative");
            book.setAvailableNumberOfCopies(bookUpdateDto.getAvailableNumberOfCopies());
        }

        if(bookUpdateDto.getTotalNumberOfCopies() != null)
        {
            if(bookUpdateDto.getTotalNumberOfCopies() < 0)
                throw new IllegalArgumentException("Number of copies cannot be negative");
            book.setTotalNumberOfCopies(bookUpdateDto.getTotalNumberOfCopies());
        }

        if(bookUpdateDto.getYear() != null)
        {
            if(bookUpdateDto.getYear() < 0)
                throw new IllegalArgumentException("Year cannot be negative");
            book.setYear(bookUpdateDto.getYear());
        }

        if(bookUpdateDto.getTitle() != null)
        {
            Optional<Book> booksWithSameNameAndAuthor = bookRepository.findByTitleAndAuthorId(bookUpdateDto.getTitle(), book.getAuthor().getId());
            if(booksWithSameNameAndAuthor.isPresent())
                throw new IllegalArgumentException("Book with title " + bookUpdateDto.getTitle() + " and author " + book.getAuthor().getFirstName() + " " + book.getAuthor().getLastName() + " already exists");
            book.setTitle(bookUpdateDto.getTitle());
        }

        if(bookUpdateDto.getAuthorId() != null)
        {
            Author author = authorRepository.findById(bookUpdateDto.getAuthorId()).orElseThrow(() -> new IllegalArgumentException("Author with id " + bookUpdateDto.getAuthorId() + " does not exist"));
            book.setAuthor(author);
        }

        if(bookUpdateDto.getCategory() != null)
        {
            book.setCategory(bookUpdateDto.getCategory());
        }

        bookRepository.save(book);
    }

    public List<NewBookDto> getAllBooks(String category)
    {
        List<Book>books = new ArrayList<>();
        //Books need to be sorted by title:
        if(Objects.equals(category, "all")) {
            books = bookRepository.findAllByOrderByTitle();
        }
        else {
            books = bookRepository.findAllByCategoryOrderByTitle(category);
        }
        List<NewBookDto> returnBooks = new ArrayList<>();
        for(Book book : books)
        {
            System.out.println(book.getTitle());
            NewBookDto newBookDto = NewBookDto.builder()
                    .title(book.getTitle())
                    .year(book.getYear())
                    .totalNumberOfCopies(book.getTotalNumberOfCopies())
                    .availableNumberOfCopies(book.getAvailableNumberOfCopies())
                    .authorId(book.getAuthor().getId())
                    .category(book.getCategory())
                    .build();
            returnBooks.add(newBookDto);
        }
        return returnBooks;
    }



}
