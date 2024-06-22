package com.pjatk.bookstore.bookshop.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookResponse {

    private String author;
    private String title;
    private String genre;
    private BigDecimal price;
    private Integer numberOfPages;
    private Integer websiteVisits;
    private Integer availableCopies;
}
