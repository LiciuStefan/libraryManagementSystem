package com.example.librarymanagementsystem.repositories;


import com.example.librarymanagementsystem.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

    @Query("SELECT b FROM books b WHERE b.title = :title and b.author.id = :authorId")
    Optional<Book>findByTitleAndAuthorId(String title, Long authorId);

    @Query("SELECT b from books b WHERE b.availableNumberOfCopies > 0 order by b.title")
    List<Book> findAllByOrderByTitle();

    @Query("SELECT b FROM books b WHERE b.category = :category")
    List<Book>findAllByCategoryOrderByTitle(String category);
}
