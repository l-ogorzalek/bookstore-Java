package com.pjatk.bookstore.bookshop.repository;

import com.pjatk.bookstore.bookshop.model.Author;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AuthorRepository extends CrudRepository<Author, UUID> {
}
