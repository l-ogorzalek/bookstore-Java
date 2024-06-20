package com.pjatk.bookstore.bookshop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
public class Book {
    @Id
    @UuidGenerator
    private UUID id;
    private String author;
    private String title;
    private String genre;
    private BigDecimal price;
    private Integer numberOfPages;
    private Integer websiteVisits;
    private Integer availableCopies;

    // Change String author to Author author
}
