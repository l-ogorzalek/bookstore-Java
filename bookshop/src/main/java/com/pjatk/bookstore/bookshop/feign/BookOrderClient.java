package com.pjatk.bookstore.bookshop.feign;

import com.book_api.openapi.model.BookOrderRequest;
import com.pjatk.bookstore.bookshop.FeignConfig;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "book-order", url = "http://localhost:8081/orders", configuration = FeignConfig.class)
public interface BookOrderClient {

    @PostMapping("/createOrder")
    ResponseEntity<List<BookOrderRequest>> createOrder(@RequestBody @Valid List<BookOrderRequest> bookOrderRequest);
}
