package com.example.SanGeets.Exceptions;

public class AdminAlreadyCreated extends RuntimeException {
    public AdminAlreadyCreated(String message) {
        super(message);
    }
}
