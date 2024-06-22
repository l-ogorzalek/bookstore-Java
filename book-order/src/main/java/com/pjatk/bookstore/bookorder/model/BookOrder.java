package com.pjatk.bookstore.bookorder.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Entity
public class BookOrder {

    @Id
    private UUID bookId;
    private Integer orderAmount;

    public BookOrder() {
    }
}
