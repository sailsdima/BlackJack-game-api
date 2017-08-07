
package com.example.blackjack.entity;

import com.example.blackjack.engine.GameEngine;
import com.example.blackjack.enumeration.Rank;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Created by dima on 07.08.17.
 */
public class GameInfo {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    private Game game;
    private List<Card> playerCards = new ArrayList<>();
    private List<Card> dealerCards = new ArrayList<>();

    @JsonIgnore
    private Card hiddenCard;

    @JsonIgnore
    private Deque<Card> deck;


    public GameInfo(Game game) {
        this.game = game;
        fillDealerCards(game.getDealerCardIds());
        fillPlayerCards(game.getPlayerCardIds());
        hiddenCard = GameEngine.getCardById(game.getHiddenCardId());
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

    public int getPlayerPoints() {
        return getPoints(playerCards);
    }

    public int getDealerPoints() {
        return getPoints(dealerCards);
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
