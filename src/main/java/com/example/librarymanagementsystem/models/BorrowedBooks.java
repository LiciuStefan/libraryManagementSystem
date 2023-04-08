package com.example.librarymanagementsystem.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "borrowed_books")
@Table(name = "borrowed_books")
@SQLDelete(sql = "UPDATE borrowed_books SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class BorrowedBooks {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private Long userId;

    @ManyToOne
    private Book book;

    @NotNull
    private String status;

    private boolean deleted = Boolean.FALSE;
}
