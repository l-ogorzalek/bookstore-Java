package com.pjatk.bookstore.bookshop.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidRequestError extends RuntimeException {

    private String message;
    private String details;

    public InvalidRequestError() {
        super();
    }

    public InvalidRequestError(String message, String details) {
        this.message = message;
        this.details = details;
    }

    @Override
    public String toString() {
        return "InvalidRequestError{" +
                "message='" + message + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
