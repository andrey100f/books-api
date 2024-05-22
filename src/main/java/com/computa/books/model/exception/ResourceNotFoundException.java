package com.computa.books.model.exception;

/**
 * Exception thrown when a request resource is not found
 * This exception is typically used to indicate that a specific resource could not be located
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
