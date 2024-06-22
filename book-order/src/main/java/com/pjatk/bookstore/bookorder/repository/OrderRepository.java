package com.pjatk.bookstore.bookorder.repository;

import com.pjatk.bookstore.bookorder.model.BookOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<BookOrder, UUID> {
}
