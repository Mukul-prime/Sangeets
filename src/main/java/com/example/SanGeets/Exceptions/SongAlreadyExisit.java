package com.example.SanGeets.Exceptions;

public class SongAlreadyExisit extends RuntimeException {
    public SongAlreadyExisit(String message) {
        super(message);
    }
}
