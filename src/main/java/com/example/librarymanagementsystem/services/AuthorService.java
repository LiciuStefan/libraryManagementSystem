package com.example.librarymanagementsystem.services;


import com.example.librarymanagementsystem.dtos.NewAuthorDto;
import com.example.librarymanagementsystem.models.Author;
import com.example.librarymanagementsystem.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuthorService {

    public AuthorRepository authorRepository;


    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public void addAuthor(NewAuthorDto newAuthorDto) {
        if(authorRepository.findAuthorByName(newAuthorDto.getFirstName(), newAuthorDto.getLastName()).isPresent()) {
            throw new RuntimeException("Author already exists");
        }

        Author author = Author.builder()
                .firstName(newAuthorDto.getFirstName())
                .lastName(newAuthorDto.getLastName())
                .build();
        authorRepository.save(author);

    }

    public Iterable<Author> getAllAuthors() {
        return authorRepository.findAll();
    }
}
