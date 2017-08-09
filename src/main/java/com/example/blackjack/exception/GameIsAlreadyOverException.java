package com.example.blackjack.exception;

/**
 * Created by dima on 08.08.17.
 */
public class GameIsAlreadyOverException extends RuntimeException{

    public GameIsAlreadyOverException(String message){
        super(message);
    }

}
