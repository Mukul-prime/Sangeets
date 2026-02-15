package com.example.SanGeets.Exceptions;

public class UserNotfound extends RuntimeException {
    public UserNotfound(String message) {
        super(message);
    }
}
