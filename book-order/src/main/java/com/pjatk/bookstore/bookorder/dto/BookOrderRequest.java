package com.pjatk.bookstore.bookorder.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class BookOrderRequest {

    private UUID bookId;
    private String title;
    private Integer websiteVisits;
}
