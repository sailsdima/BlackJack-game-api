package com.example.blackjack.enumeration;

/**
 * Created by dima on 06.08.17.
 */
public enum Status {

    IN_PROGRESS("In progress"), DRAW("Draw"), DEALER_WON("Dealer won"),
    PLAYER_WON("User won"), CANCELLED("Cancelled");

    String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
