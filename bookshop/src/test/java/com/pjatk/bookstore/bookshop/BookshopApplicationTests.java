package com.pjatk.bookstore.bookshop;

import com.pjatk.bookstore.bookshop.dto.BookCreateRequest;
import com.pjatk.bookstore.bookshop.dto.BookPurchaseRequest;
import com.pjatk.bookstore.bookshop.dto.BookPurchaseResponse;
import com.pjatk.bookstore.bookshop.dto.BookResponse;
import com.pjatk.bookstore.bookshop.model.Author;
import com.pjatk.bookstore.bookshop.model.Book;
import com.pjatk.bookstore.bookshop.repository.AuthorRepository;
import com.pjatk.bookstore.bookshop.repository.BookRepository;
import com.pjatk.bookstore.bookshop.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BookshopApplicationTests {

	@Mock
	private BookRepository bookRepository;

	@Mock
	private AuthorRepository authorRepository;

	@InjectMocks
	private BookService bookService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testListBooks() {
		Book book = new Book();
		book.setTitle("Test Book");
		book.setAuthor(new Author());
		book.setGenre("Test Genre");
		book.setAvailableCopies(10);
		book.setPrice(BigDecimal.valueOf(19.99));

		when(bookRepository.findAll()).thenReturn(Collections.singletonList(book));

		List<BookResponse> books = bookService.listBooks(null, null, null, null, null);

		assertEquals(1, books.size());
		verify(bookRepository, times(1)).findAll();
	}

	@Test
	void testBooksPost() {
		BookCreateRequest request = new BookCreateRequest();
		request.setTitle("Test Book");
		request.setAuthorId(UUID.randomUUID());
		request.setGenre("Test Genre");
		request.setAvailableCopies(10);
		request.setPrice(BigDecimal.valueOf(19.99));

		Author author = new Author();
		when(authorRepository.findById(request.getAuthorId())).thenReturn(Optional.of(author));

		Book book = new Book();
		when(bookRepository.save(any(Book.class))).thenReturn(book);

		BookResponse response = bookService.booksPost(request);

		assertNotNull(response);
		verify(bookRepository, times(1)).save(any(Book.class));
		verify(authorRepository, times(1)).findById(request.getAuthorId());
	}

	@Test
	void testGetBookById() {
		UUID bookId = UUID.randomUUID();
		Book book = new Book();
		book.setId(bookId);

		when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

		BookResponse response = bookService.getBookById(bookId);

		assertNotNull(response);
		verify(bookRepository, times(1)).findById(bookId);
	}

	@Test
	void testUpdateBook() {
		UUID bookId = UUID.randomUUID();
		BookCreateRequest request = new BookCreateRequest();
		request.setTitle("Updated Book");
		request.setAuthorId(UUID.randomUUID());
		request.setGenre("Updated Genre");
		request.setAvailableCopies(5);
		request.setPrice(BigDecimal.valueOf(29.99));

		Book book = new Book();
		book.setId(bookId);

		Author author = new Author();
		when(authorRepository.findById(request.getAuthorId())).thenReturn(Optional.of(author));
		when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
		when(bookRepository.save(any(Book.class))).thenReturn(book);

		BookResponse response = bookService.updateBook(bookId, request);

		assertNotNull(response);
		verify(bookRepository, times(1)).findById(bookId);
		verify(bookRepository, times(1)).save(any(Book.class));
		verify(authorRepository, times(1)).findById(request.getAuthorId());
	}

	@Test
	void testDeleteBook() {
		UUID bookId = UUID.randomUUID();
		Book book = new Book();
		book.setId(bookId);

		when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
		doNothing().when(bookRepository).delete(book);

		bookService.deleteBook(bookId);

		verify(bookRepository, times(1)).findById(bookId);
		verify(bookRepository, times(1)).delete(book);
	}

	@Test
	void testPurchaseBook() {
		UUID bookId = UUID.randomUUID();
		BookPurchaseRequest request = new BookPurchaseRequest();
		request.setId(bookId);
		request.setTitle("Test Book");
		request.setAuthor(String.valueOf(new Author()));
		request.setGenre("Test Genre");
		request.setPrice(BigDecimal.valueOf(19.99));
		request.setNumberOfPages(100);

		Book book = new Book();
		book.setId(bookId);
		book.setAvailableCopies(10);

		when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
		when(bookRepository.save(any(Book.class))).thenReturn(book);

		BookPurchaseResponse response = bookService.purchaseBook(request);

		assertNotNull(response);
		verify(bookRepository, times(1)).findById(bookId);
		verify(bookRepository, times(1)).save(any(Book.class));
	}
}
