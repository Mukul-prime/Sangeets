package com.example.SanGeets.Exceptions;

public class SondDeleteAlready extends RuntimeException {
    public SondDeleteAlready(String message) {
        super(message);
    }
}
