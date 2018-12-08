package org.ionedan.ufortnight.libraryservice.exceptions;

import lombok.Getter;

public class AuthorNotFoundException extends RuntimeException {
    @Getter
    private final String message;

    public AuthorNotFoundException(String message) {
        this.message = message;
    }

}
