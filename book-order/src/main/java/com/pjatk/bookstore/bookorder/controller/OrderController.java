package com.pjatk.bookstore.bookorder.controller;

import com.book_api.openapi.api.OrderControllerApi;
import com.pjatk.bookstore.bookorder.dto.BookOrderRequest;
import com.pjatk.bookstore.bookorder.model.BookOrder;
import com.pjatk.bookstore.bookorder.service.OrderService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController implements OrderControllerApi {

    private final OrderService service;

    @PostMapping("/createOrder")
    public ResponseEntity<List<BookOrder>> createOrder(@RequestBody @Valid List<BookOrderRequest> bookOrderRequest) {
        List<BookOrder> orders = service.processRequest(bookOrderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(orders);
    }

    @GetMapping("/print")
    public void printOrder (HttpServletResponse response) {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=order.pdf");

        service.generatePdf(response);
    }
}
