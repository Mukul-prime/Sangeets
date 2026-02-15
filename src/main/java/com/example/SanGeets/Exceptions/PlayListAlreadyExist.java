package com.example.SanGeets.Exceptions;

public class PlayListAlreadyExist extends RuntimeException {
    public PlayListAlreadyExist(String message) {
        super(message);
    }
}
