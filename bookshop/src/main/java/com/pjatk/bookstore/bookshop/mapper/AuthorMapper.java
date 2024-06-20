package com.pjatk.bookstore.bookshop.mapper;

import com.pjatk.bookstore.bookshop.dto.AuthorResponse;
import com.pjatk.bookstore.bookshop.dto.AuthorCreateRequest;
import com.pjatk.bookstore.bookshop.model.Author;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorResponse toAuthorResponse(Author author);
    AuthorResponse toAuthorResponse(AuthorCreateRequest authorCreateRequest);

    Author toAuthor(AuthorCreateRequest authorCreateRequest);
    Author toAuthor(AuthorResponse authorResponse);

    AuthorCreateRequest toAuthorCreateRequest(Author author);
    AuthorCreateRequest toAuthorCreateRequest(AuthorResponse authorResponse);
}
