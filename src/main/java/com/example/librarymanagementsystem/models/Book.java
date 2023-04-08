package com.example.librarymanagementsystem.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "books")
@Table(name = "books")
@SQLDelete(sql = "UPDATE books SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;

    @NotNull
    private String title;

    @NotNull
    private String category;

    @NotNull
    private Integer year;

    @NotNull
    private Long totalNumberOfCopies;

    @NotNull
    private Long availableNumberOfCopies;

    private boolean deleted = Boolean.FALSE;
}
