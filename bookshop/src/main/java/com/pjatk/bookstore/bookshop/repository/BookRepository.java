package com.pjatk.bookstore.bookshop.repository;

import com.pjatk.bookstore.bookshop.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
}
