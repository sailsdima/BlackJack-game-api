
package com.example.blackjack.entity;

import com.example.blackjack.engine.GameEngine;
import com.example.blackjack.enumeration.Rank;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.*;

/**
 * Created by dima on 07.08.17.
 */
public class GameInfo {

    private Game game;
    public int getPlayerPoints() {
        return getPoints(playerCards);
    }
    public int getDealerPoints() {
        return getPoints(dealerCards);
    }


    @JsonIgnore
    private Card hiddenCard;

    @JsonIgnore
    private Deque<Card> deck;
    private List<Card> playerCards = new ArrayList<>();
    private List<Card> dealerCards = new ArrayList<>();


    public GameInfo(Game game) {
        this.game = game;
        int [] a = game.getDealerCardIds();
        fillDealerCards(a);
        fillPlayerCards(game.getPlayerCardIds());
        hiddenCard = GameEngine.getCardById(game.getHiddenCardId());
        List<Card> usedCards = new ArrayList<>(playerCards);
        usedCards.addAll(dealerCards);
        if (hiddenCard != null)
            usedCards.add(hiddenCard);
        deck = GameEngine.getShuffledDeckWithoutCards(usedCards);
    }

    private void fillPlayerCards(int[] cardIds) {
        for (int id : cardIds) {
            playerCards.add(GameEngine.getCardById(id));
        }
    }

    private void fillDealerCards(int[] cardIds) {
        for (int id : cardIds) {
            dealerCards.add(GameEngine.getCardById(id));
        }
    }



    private int getPoints(List<Card> cards) {
        int points = 0;
        int acesCount = 0;
        for (Card card : cards) {
            if (!card.getRank().equals(Rank.ACE)) {
                points += card.getRank().getValue();
            } else {
                acesCount++;
            }
        }
        while (acesCount-- > 0)
            if (points <= 10)
                points += Rank.ACE.getValue();
            else points += 1;
        return points;
    }

    public void addPlayerCard(Card card) {
        playerCards.add(card);
    }

    public void addDealerCard(Card card) {
        dealerCards.add(card);
    }

    public void showHiddenCard() {
        dealerCards.add(hiddenCard);
    }

    public Card getHiddenCard() {
        return hiddenCard;
    }

    public void setHiddenCard(Card hiddenCard) {
        this.hiddenCard = hiddenCard;
    }

    public Game getGame() {
        return game;
    }

    public Deque<Card> getDeck() {
        return deck;
    }

    public void setDeck(Deque<Card> deck) {
        this.deck = deck;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<Card> getPlayerCards() {
        return playerCards;
    }

    public void setPlayerCards(List<Card> playerCards) {
        this.playerCards = playerCards;
    }

    public List<Card> getDealerCards() {
        return dealerCards;
    }

    public void setDealerCards(List<Card> dealerCards) {
        this.dealerCards = dealerCards;
    }

}
