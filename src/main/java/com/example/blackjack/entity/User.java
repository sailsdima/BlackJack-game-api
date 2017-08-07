package com.example.blackjack.entity;

import com.example.blackjack.engine.GameEngine;
import com.example.blackjack.enumeration.Rank;
import com.example.blackjack.enumeration.Suit;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

/**
 * Created by dima on 28.07.17.
 */

@Entity
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    private String name;

    private BigDecimal balance = new BigDecimal(0);

    public User() {
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void replenishBalance(BigDecimal amount) {
        balance = balance.add(amount);
    }
}
