package com.pjatk.bookstore.bookshop.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
public class Book {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id")
    private Author author;

    private String title;
    private String genre;
    private BigDecimal price;
    private Integer numberOfPages;
    private Integer websiteVisits;
    private Integer availableCopies;
}
