package com.pjatk.bookstore.bookshop.service;

import com.book_api.openapi.model.BookOrderRequest;
import com.pjatk.bookstore.bookshop.feign.BookOrderClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final BookOrderClient bookOrderClient;

    public void generateOrderReport(List<BookOrderRequest> bookOrderRequests) {
        bookOrderClient.createOrder(bookOrderRequests);
    }
}
