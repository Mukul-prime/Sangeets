package com.example.SanGeets.Exceptions;

public class AlreadyUserin extends RuntimeException {
    public AlreadyUserin(String message) {
        super(message);
    }
}
