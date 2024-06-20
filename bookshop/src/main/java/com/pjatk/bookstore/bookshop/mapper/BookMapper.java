package com.pjatk.bookstore.bookshop.mapper;

import com.pjatk.bookstore.bookshop.dto.BookCreateRequest;
import com.pjatk.bookstore.bookshop.dto.BookPurchaseRequest;
import com.pjatk.bookstore.bookshop.dto.BookPurchaseResponse;
import com.pjatk.bookstore.bookshop.dto.BookResponse;
import com.pjatk.bookstore.bookshop.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    BookResponse toBookResponse(Book book);
    BookResponse toBookResponse(BookCreateRequest bookCreateRequest);
    BookResponse toBookResponse(BookPurchaseRequest bookPurchaseRequest);
    BookResponse toBookResponse(BookPurchaseResponse bookPurchaseResponse);

    Book toBook(BookCreateRequest bookCreateRequest);
    Book toBook(BookResponse bookResponse);
    Book toBook(BookPurchaseRequest bookPurchaseRequest);
    Book toBook(BookPurchaseResponse bookPurchaseResponse);

    BookCreateRequest toBookCreateRequest(Book book);
    BookCreateRequest toBookCreateRequest(BookResponse bookResponse);
    BookCreateRequest toBookCreateRequest(BookPurchaseRequest bookPurchaseRequest);
    BookCreateRequest toBookCreateRequest(BookPurchaseResponse bookPurchaseResponse);

    BookPurchaseResponse toBookPurchaseResponse(Book book);
}