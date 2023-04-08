package com.example.librarymanagementsystem.controllers;

import com.example.librarymanagementsystem.services.BorrowedBooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/borrowedBooks")
public class BorrowedBooksController {

    private final BorrowedBooksService borrowedBooksService;

    @Autowired
    public BorrowedBooksController(BorrowedBooksService borrowedBooksService) {
        this.borrowedBooksService = borrowedBooksService;
    }

    @PostMapping("/borrow/{id}")
    public ResponseEntity<?> borrowBook(@PathVariable Long id, @RequestParam Long userId){
        borrowedBooksService.borrowBook(id, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/return/{id}")
    public ResponseEntity<?> returnBook(@PathVariable Long id, @RequestParam Long userId){
        borrowedBooksService.returnBook(id, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
