package com.example.blackjack.entity;

import com.example.blackjack.enumeration.Rank;
import com.example.blackjack.enumeration.Suit;

import javax.persistence.Entity;
import java.util.*;

/**
 * Created by dima on 06.08.17.
 */
public class Card {

    private int id;

    private Rank rank;
    private Suit suit;

    public Card(int id, Rank rank, Suit suit) {
        this.id = id;
        this.rank = rank;
        this.suit = suit;
    }

    public int getId() {
        return id;
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        return id == card.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
