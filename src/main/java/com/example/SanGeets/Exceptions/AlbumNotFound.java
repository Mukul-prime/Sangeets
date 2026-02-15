package com.example.SanGeets.Exceptions;

public class AlbumNotFound extends RuntimeException {
    public AlbumNotFound(String message) {
        super(message);
    }
}
