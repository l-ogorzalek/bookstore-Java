package com.pjatk.bookstore.bookshop.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class AuthorResponse {

    private UUID id;
    private String name;
}
