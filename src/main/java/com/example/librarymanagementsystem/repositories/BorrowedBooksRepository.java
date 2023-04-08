package com.example.librarymanagementsystem.repositories;

import com.example.librarymanagementsystem.models.BorrowedBooks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowedBooksRepository extends JpaRepository<BorrowedBooks, Long> {

    @Query("SELECT b FROM borrowed_books b WHERE b.book.id = :id")
    List<BorrowedBooks> findAllById(Long id);
}
