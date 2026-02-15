package com.example.SanGeets.Exceptions;

public class ArtistNotFound extends RuntimeException {
    public ArtistNotFound(String message) {
        super(message);
    }
}
