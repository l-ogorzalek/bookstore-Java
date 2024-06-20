package com.pjatk.bookstore.bookshop.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;

@ControllerAdvice
public class BookshopExceptionHandler extends ResponseStatusExceptionHandler {
    private final Logger logger = LogManager.getLogger(BookshopExceptionHandler.class);

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid request")
    @ExceptionHandler(InvalidRequestError.class)
    public void handleInvalidRequestError(HttpServletRequest req, Exception ex) {
        logger.warn("Invalid request at {}", req.getRequestURI() + ": " + ex.getMessage());
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource not found")
    @ExceptionHandler(ResourceNotFoundError.class)
    public void handleResourceNotFoundError(HttpServletRequest req, ResourceNotFoundError ex) {
        String resourceType = "Resource";
        if (ex.getResourceType() != null) {
            resourceType = ex.getResourceType();
        }
        logger.warn(resourceType + " not found at " + req.getRequestURL() + ": " + ex.getMessage());
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Resource forbidden")
    @ExceptionHandler(ResourceForbiddenError.class)
    public void handleResourceForbiddenError(HttpServletRequest req, ResourceForbiddenError ex) {
        String resourceType = "Resource";
        if (ex.getResourceType() != null) {
            resourceType = ex.getResourceType();
        }
        logger.warn(resourceType + " forbidden at " + req.getRequestURL() + ": " + ex.getMessage());
    }
}
