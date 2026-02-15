package com.example.SanGeets.Exceptions;

public class LanguageAlreadyExist extends RuntimeException {
    public LanguageAlreadyExist(String message) {
        super(message);
    }
}
