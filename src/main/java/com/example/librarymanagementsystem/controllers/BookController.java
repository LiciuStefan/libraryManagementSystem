package com.example.librarymanagementsystem.controllers;


import com.example.librarymanagementsystem.dtos.BookUpdateDto;
import com.example.librarymanagementsystem.dtos.NewBookDto;
import com.example.librarymanagementsystem.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBook(@RequestBody NewBookDto newBookDto) {
        bookService.addBook(newBookDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody BookUpdateDto bookUpdateDto) {
        bookService.updateBook(id, bookUpdateDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> getAllBooks(@RequestParam(defaultValue = "all") String category) {
        return new ResponseEntity<>(bookService.getAllBooks(category), HttpStatus.OK);
    }
}
