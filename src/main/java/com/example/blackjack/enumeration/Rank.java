package com.example.blackjack.enumeration;

/**
 * Created by dima on 06.08.17.
 */
public enum Rank {

    TWO("Two", 2), THREE("Three", 3), FOUR("Four", 4), FIVE("Five", 5), SIX("Six", 6),
    SEVEN("Seven", 7), EIGHT("Eight", 8), NINE("Nine", 9), TEN("Ten", 10),
    JACK("Jack", 10), QUEEN("Queen", 10), KING("King", 10), ACE("Ace", 11);

    String name;
    int value;

    Rank(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

}
