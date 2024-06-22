package com.pjatk.bookstore.bookshop.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

    private String message;
    private String details;
    private String resourceType;

    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String message, String details, String resourceType) {
        this.message = message;
        this.details = details;
        this.resourceType = resourceType;
    }

    @Override
    public String toString() {
        return "ResourceNotFoundError{" +
                "message='" + message + '\'' +
                ", details='" + details + '\'' +
                ", resourceType='" + resourceType + '\'' +
                '}';
    }
}