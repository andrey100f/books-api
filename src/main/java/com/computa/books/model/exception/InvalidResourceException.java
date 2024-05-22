package com.computa.books.model.exception;

/**
 * Exception thrown when an invalid resource is encountered
 * This exception is typically used to indicate that the provided data for a resource is invalid
 */
public class InvalidResourceException extends RuntimeException {

    public InvalidResourceException(String message) {
        super(message);
    }

}
