package com.example.blackjack.entity;

import com.example.blackjack.enumeration.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dima on 07.08.17.
 */

@Entity
public class Game {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @ManyToOne
    private User user;

    private BigDecimal bet;

    private Date date = new Date();
    private Status status = Status.IN_PROGRESS;

    @JsonIgnore
    private int[] playerCardIds = new int[]{};
    @JsonIgnore
    private int[] dealerCardIds = new int[]{};
    @JsonIgnore
    private int hiddenCardId;

    public Game() {
    }

    public Game(User user, BigDecimal bet) {
        this.user = user;
        this.bet = bet;
    }

    public Game(GameInfo gameInfo){
        this.id = gameInfo.getGame().getId();
        this.user = gameInfo.getGame().getUser();
        this.bet = gameInfo.getGame().getBet();
        this.date = gameInfo.getGame().getDate();
        this.status = gameInfo.getGame().getStatus();
        this.hiddenCardId = gameInfo.getHiddenCard().getId();
        playerCardIds = new int[gameInfo.getPlayerCards().size()];
        dealerCardIds = new int[gameInfo.getDealerCards().size()];

        for (int i = 0; i < playerCardIds.length; i++) {
            playerCardIds[i] = gameInfo.getPlayerCards().get(i).getId();
        }
        for (int i = 0; i < dealerCardIds.length; i++) {
            dealerCardIds[i] = gameInfo.getDealerCards().get(i).getId();
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public BigDecimal getBet() {
        return bet;
    }

    public void setBet(BigDecimal bet) {
        this.bet = bet;
    }

    public int[] getPlayerCardIds() {
        return playerCardIds;
    }

    public void setPlayerCardIds(int[] playerCardIds) {
        this.playerCardIds = playerCardIds;
    }

    public int[] getDealerCardIds() {
        return dealerCardIds;
    }

    public void setDealerCardIds(int[] dealerCardIds) {
        this.dealerCardIds = dealerCardIds;
    }

    public int getHiddenCardId() {
        return hiddenCardId;
    }

    public void setHiddenCardId(int hiddenCardId) {
        this.hiddenCardId = hiddenCardId;
    }
}

