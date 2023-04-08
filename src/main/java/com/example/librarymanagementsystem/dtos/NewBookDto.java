package com.example.librarymanagementsystem.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewBookDto {

    private String title;
    private Long authorId;

    private String category;

    private Integer year;

    private Long totalNumberOfCopies;
    private Long availableNumberOfCopies;
}
