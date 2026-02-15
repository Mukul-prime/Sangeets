package com.example.SanGeets.Exceptions;

public class CountryAlreadyExist extends RuntimeException {
    public CountryAlreadyExist(String message) {
        super(message);
    }
}
