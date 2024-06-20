package com.pjatk.bookstore.bookshop.controller;

import com.book_api.openapi.api.BookControllerApi;
import com.pjatk.bookstore.bookshop.dto.BookCreateRequest;
import com.pjatk.bookstore.bookshop.dto.BookPurchaseRequest;
import com.pjatk.bookstore.bookshop.dto.BookPurchaseResponse;
import com.pjatk.bookstore.bookshop.dto.BookResponse;
import com.pjatk.bookstore.bookshop.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController implements BookControllerApi {

    private final BookService service;

    @GetMapping("/listBooks")
    public ResponseEntity<List<BookResponse>> listAndFilterBooks(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "genre", required = false) String genre,
            @RequestParam(value = "available", required = false) Integer available,
            @RequestParam(value = "price", required = false) BigDecimal price
    ) {
        List<BookResponse> filteredBooks = service.listBooks(title, author, genre, available, price);
        return ResponseEntity.ok(filteredBooks);
    }

    @PostMapping("/addBook")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookResponse> postBook(@RequestBody @Valid BookCreateRequest bookCreateRequest) {
        BookResponse addedBook = service.booksPost(bookCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedBook);
    }

    @GetMapping("/listBooks/{id}")
    public ResponseEntity<BookResponse> GetBookId(@PathVariable("id") UUID id) {
        BookResponse bookResponse = service.getBookById(id);
        return ResponseEntity.ok(bookResponse);
    }

    @PutMapping("/updateBook/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookResponse> updateBook(@PathVariable("id") UUID id, @Valid BookCreateRequest bookCreateRequest) {
        BookResponse updatedBook = service.updateBook(id, bookCreateRequest);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/deleteBook/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") UUID id) {
        service.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/purchase/{id}")
    public ResponseEntity<BookPurchaseResponse> purchaseBook(@RequestBody @Valid BookPurchaseRequest bookPurchaseRequest) {
        BookPurchaseResponse bookPurchaseResponse = service.purchaseBook(bookPurchaseRequest);
        return ResponseEntity.ok(bookPurchaseResponse);
    }
}
