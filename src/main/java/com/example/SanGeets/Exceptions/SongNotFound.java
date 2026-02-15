package com.example.SanGeets.Exceptions;

public class SongNotFound extends RuntimeException {
    public SongNotFound(String message) {
        super(message);
    }
}
