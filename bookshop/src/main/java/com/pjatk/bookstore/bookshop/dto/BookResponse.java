package com.pjatk.bookstore.bookshop.dto;

import lombok.Data;

import java.math.BigDecimal;
// import java.util.UUID;

@Data
public class BookResponse {

    // private UUID id;
    private String author;
    private String title;
    private String genre;
    private BigDecimal price;
    private Integer numberOfPages;
    private Integer websiteVisits;
    private Integer availableCopies;
}
