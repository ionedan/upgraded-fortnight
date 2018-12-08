package org.ionedan.ufortnight.libraryservice.exceptions;

import lombok.Getter;

public class BookNotFoundException extends RuntimeException {
    @Getter
    private final String message;

    public BookNotFoundException(String message) {
        this.message = message;
    }
}
