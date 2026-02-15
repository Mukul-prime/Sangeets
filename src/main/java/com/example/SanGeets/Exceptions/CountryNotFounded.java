package com.example.SanGeets.Exceptions;

public class CountryNotFounded extends RuntimeException {
    public CountryNotFounded(String message) {
        super(message);
    }
}
