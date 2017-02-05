package com.apsis.exception;

/**
 * The exception class for handling ResourceNotFoundException
 */
public class ResourceNotFoundException extends RuntimeException {
    @SuppressWarnings("unused")
    public ResourceNotFoundException() {
        super();
    }

    @SuppressWarnings("unused")
    public ResourceNotFoundException(String message) {
        super(message);
    }

}
