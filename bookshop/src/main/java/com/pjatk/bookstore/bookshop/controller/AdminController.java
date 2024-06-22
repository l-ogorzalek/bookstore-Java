package com.pjatk.bookstore.bookshop.controller;

import com.book_api.openapi.api.AdminControllerApi;
import com.book_api.openapi.model.BookOrderRequest;
import com.pjatk.bookstore.bookshop.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController implements AdminControllerApi {

    private final AdminService service;

    @PostMapping("/order-report")
    @PreAuthorize("hasRole('ADMIN')")
    public void generateOrderReport(@RequestBody @Valid List<BookOrderRequest> bookOrderRequests) {
        service.generateOrderReport(bookOrderRequests);
    }

}
