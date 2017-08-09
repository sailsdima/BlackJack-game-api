package com.example.blackjack.exception;

/**
 * Created by dima on 08.08.17.
 */
public class ForbiddenGameAccessException extends RuntimeException{

    public ForbiddenGameAccessException(String message) {
        super(message);
    }
}
