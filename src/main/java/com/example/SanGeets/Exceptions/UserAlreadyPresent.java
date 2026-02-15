package com.example.SanGeets.Exceptions;

public class UserAlreadyPresent extends RuntimeException {
    public UserAlreadyPresent(String message) {
        super(message);
    }
}
