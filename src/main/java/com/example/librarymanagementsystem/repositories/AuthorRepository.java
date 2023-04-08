package com.example.librarymanagementsystem.repositories;

import com.example.librarymanagementsystem.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT a FROM authors a WHERE a.firstName = ?1 AND a.lastName = ?2")
    Optional<Author> findAuthorByName(String firstName, String lastName);
}
