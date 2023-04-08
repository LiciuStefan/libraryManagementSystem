package com.example.librarymanagementsystem.controllers;


import com.example.librarymanagementsystem.dtos.NewAuthorDto;
import com.example.librarymanagementsystem.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAuthor(@RequestBody NewAuthorDto newAuthorDto) {
        authorService.addAuthor(newAuthorDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllAuthors() {
        return new ResponseEntity<>(authorService.getAllAuthors(), HttpStatus.OK);
    }

}
