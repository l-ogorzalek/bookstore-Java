package com.pjatk.bookstore.bookshop.service;

import com.pjatk.bookstore.bookshop.dto.BookCreateRequest;
import com.pjatk.bookstore.bookshop.dto.BookPurchaseRequest;
import com.pjatk.bookstore.bookshop.dto.BookPurchaseResponse;
import com.pjatk.bookstore.bookshop.dto.BookResponse;
import com.pjatk.bookstore.bookshop.exception.InvalidRequestException;
import com.pjatk.bookstore.bookshop.exception.ResourceNotFoundException;
import com.pjatk.bookstore.bookshop.mapper.BookMapper;
import com.pjatk.bookstore.bookshop.model.Author;
import com.pjatk.bookstore.bookshop.model.Book;
import com.pjatk.bookstore.bookshop.repository.AuthorRepository;
import com.pjatk.bookstore.bookshop.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper = BookMapper.INSTANCE;

    public List<BookResponse> listBooks(String title, String author, String genre, Integer available, BigDecimal price) {
        validateFilters(price);

        List<Book> filteredBooks = bookRepository.findAll()
                .stream()
                .filter(book -> title == null || book.getTitle().contains(title))
                .filter(book -> author == null || book.getAuthor().getName().contains(author))
                .filter(book -> genre == null || book.getGenre().contains(genre))
                .filter(book -> available == null || Objects.equals(book.getAvailableCopies(), available))
                .filter(book -> price == null || book.getPrice().compareTo(price) <= 0)
                .toList();

        if (filteredBooks.isEmpty()) {
            throw new InvalidRequestException("Invalid request", "No books were found matching the criteria");
        }

        return filteredBooks.stream()
                .map(bookMapper::toBookResponse)
                .collect(toList());
    }

    public BookResponse booksPost(BookCreateRequest bookCreateRequest) {
        validateBookCreateRequest(bookCreateRequest);

        Book newBook = new Book();

        bookFieldsSetter(newBook, bookCreateRequest);
        Book savedBook = bookRepository.save(newBook);

        return bookMapper.toBookResponse(savedBook);
    }

    public BookResponse getBookById(UUID id) {
        validateId(id);

        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            updateBookWebsiteVisits(book);
            return bookMapper.toBookResponse(book);
        } else {
            throw new ResourceNotFoundException("Resource not found", "Book with id " + id + " was not found", id.toString());
        }
    }

    public BookResponse updateBook(UUID id, BookCreateRequest bookCreateRequest) {
        validateBookCreateRequest(bookCreateRequest);

        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isPresent()) {
            Book existingBook = optionalBook.get();

            bookFieldsSetter(existingBook, bookCreateRequest);
            Book updatedBook = bookRepository.save(existingBook);

            return bookMapper.toBookResponse(updatedBook);
        } else {
            throw new ResourceNotFoundException("Resource not found", "Book with id " + id + " was not found", id.toString());
        }
    }

    public void deleteBook(UUID id) {
        validateId(id);

        Optional<Book> bookOptional = bookRepository.findById(id);

        if (bookOptional.isPresent()) {
            Book bookToDelete = bookOptional.get();
            bookRepository.delete(bookToDelete);
        } else {
            throw new ResourceNotFoundException("Resource not found", "Book with id " + id + " was not found", id.toString());
        }
    }

    public BookPurchaseResponse purchaseBook(BookPurchaseRequest bookPurchaseRequest) {
        validateBookPurchaseRequest(bookPurchaseRequest);

        Optional<Book> optionalBook = bookRepository.findById(bookPurchaseRequest.getId());
        UUID id = bookPurchaseRequest.getId();

        if (optionalBook.isPresent()) {
            checkAvailableCopies(optionalBook.get().getAvailableCopies());
            Book book = optionalBook.get();
            book.setAvailableCopies(book.getAvailableCopies() - 1);
            bookRepository.save(book);

            return bookMapper.toBookPurchaseResponse(book);
        } else {
            throw new ResourceNotFoundException("Resource not found", "Book with id " + id + " was not found", id.toString());
        }
    }

    //validators

    private void validateBookCreateRequest(BookCreateRequest bookCreateRequest) {
        if (bookCreateRequest.getTitle() == null || bookCreateRequest.getAuthor() == null || bookCreateRequest.getGenre() == null ||
                bookCreateRequest.getAvailableCopies() == null || bookCreateRequest.getPrice() == null) {
            throw new InvalidRequestException("Invalid request", "All fields (title, author, genre, availableCopies, price) are required.");
        }
    }

    private void validateBookPurchaseRequest(BookPurchaseRequest bookPurchaseRequest) {
        validateId(bookPurchaseRequest.getId());
        if (bookPurchaseRequest.getAuthor() == null || bookPurchaseRequest.getTitle() == null || bookPurchaseRequest.getGenre() == null ||
                bookPurchaseRequest.getPrice() == null || bookPurchaseRequest.getNumberOfPages() == null) {
            throw new InvalidRequestException("Invalid request", "All fields (title, author, genre, availableCopies, price) are required.");
        }
    }

    private void validateId(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
    }

    private void checkAvailableCopies(Integer availableCopies) {
        if (availableCopies == 0) {
            throw new InvalidRequestException("Invalid request", "Book is out of stock");
        }
    }

    private void validateFilters(BigDecimal price) {
        if (price != null && price.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidRequestException("Invalid request", "Price cannot be negative");
        }
    }

    //utility

    private void updateBookWebsiteVisits(Book book) {
        book.setWebsiteVisits(book.getWebsiteVisits() + 1);
        bookRepository.save(book);
    }

    private void bookFieldsSetter(Book book, BookCreateRequest bookCreateRequest) {
        book.setTitle(bookCreateRequest.getTitle());

        UUID authorId = bookCreateRequest.getAuthorId();

        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new InvalidRequestException("Invalid request", "Author was not found"));


        book.setAuthor(author);
        book.setGenre(bookCreateRequest.getGenre());
        book.setAvailableCopies(bookCreateRequest.getAvailableCopies());
        book.setPrice(bookCreateRequest.getPrice());
    }
}
