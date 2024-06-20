package com.pjatk.bookstore.bookshop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Author {

    @Id
    @UuidGenerator
    private UUID id;
    private String name;
    @OneToMany
    private List<@Valid Book> books;
}
