package com.example.SanGeets.Exceptions;

public class StateAlreadyExist extends RuntimeException {
    public StateAlreadyExist(String message) {
        super(message);
    }
}
