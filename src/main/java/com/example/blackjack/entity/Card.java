package com.example.blackjack.entity;

import com.example.blackjack.enumeration.Rank;
import com.example.blackjack.enumeration.Suit;

/**
 * Created by dima on 06.08.17.
 */
public class Card {

    private int id;

    private Rank rank;
    private Suit suit;
    private int value;

    public Card(int id, Rank rank, Suit suit) {
        this.id = id;
        this.rank = rank;
        this.suit = suit;
        this.value = rank.getValue();
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

    public int getValue() {
        return value;
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
