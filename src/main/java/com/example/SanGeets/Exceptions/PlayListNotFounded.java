package com.example.SanGeets.Exceptions;

public class PlayListNotFounded extends RuntimeException {
    public PlayListNotFounded(String message) {
        super(message);
    }
}
