package com.example.SanGeets.Exceptions;

public class GenreNotFound extends RuntimeException {
    public GenreNotFound(String message) {
        super(message);
    }
}
