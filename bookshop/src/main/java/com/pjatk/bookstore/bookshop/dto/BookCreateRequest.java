package com.pjatk.bookstore.bookshop.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class BookCreateRequest {

    private UUID authorId;
    private String author;
    private String title;
    private String genre;
    private BigDecimal price;
    private Integer numberOfPages;
    private Integer availableCopies;
}
