package com.example.blackjack.entity;

import java.util.Date;

/**
 * Created by dima on 07.08.17.
 */

public class GameEvent {

    private String eventMessage;
    private Date date = new Date();

    public GameEvent(String eventMessage) {
        this.eventMessage = eventMessage;
    }

    public String getEventMessage() {
        return eventMessage;
    }

    public void setEventMessage(String eventMessage) {
        this.eventMessage = eventMessage;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
