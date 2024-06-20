package com.pjatk.bookstore.bookshop.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookPurchaseResponse {

    private String author;
    private String title;
    private String genre;
    private BigDecimal price;
    private Integer numberOfPages;
}
