package com.example.blackjack.engine;

import com.example.blackjack.entity.Card;
import com.example.blackjack.entity.Game;
import com.example.blackjack.entity.GameInfo;
import com.example.blackjack.enumeration.Rank;
import com.example.blackjack.enumeration.Status;
import com.example.blackjack.enumeration.Suit;

import java.util.*;

/**
 * Created by dima on 06.08.17.
 */
public class GameEngine {


    public GameInfo startGame(Game game) {

        GameInfo gameInfo = new GameInfo(game);

        gameInfo.setDeck(getShuffledDeck());
        gameInfo.addPlayerCard(gameInfo.getDeck().poll());
        gameInfo.addPlayerCard(gameInfo.getDeck().poll());

        gameInfo.addDealerCard(gameInfo.getDeck().poll());
        gameInfo.setHiddenCard(gameInfo.getDeck().poll());

        return gameInfo;
    }

    public GameInfo makeHit(GameInfo gameInfo) {
        checkGameIsInProgress(gameInfo);
        gameInfo.addPlayerCard(gameInfo.getDeck().poll());

        return gameInfo;
    }

    public GameInfo addCardsToDealer(GameInfo gameInfo){
        checkGameIsInProgress(gameInfo);
        gameInfo.showHiddenCard();
        while(gameInfo.getDealerPoints() < 18){
            gameInfo.addDealerCard(gameInfo.getDeck().poll());
        }
        return gameInfo;
    }


    private void checkGameIsInProgress(GameInfo gameInfo) {
        //todo
        if(!gameInfo.getGame().getStatus().equals(Status.IN_PROGRESS))
            throw new UnsupportedOperationException("Cannot make changes to this game (id: " + gameInfo.getGame().getId() +
                    "). Check the game`s status.");
    }

    private static List<Card> getDeck() {
        List<Card> deck = new ArrayList<>();
        int id = 0;
        for (Rank rank : Rank.values())
            for (Suit suit : Suit.values())
                deck.add(new Card(id++, rank, suit));
        return deck;
    }

    private static Deque<Card> getShuffledDeck() {
        List<Card> deck = getDeck();
        Collections.shuffle(deck);
        return new ArrayDeque<>(deck);
    }

    public static Deque<Card> getShuffledDeckWithoutCards(List<Card> usedCards) {
        List<Card> deck = getDeck();
        deck.removeAll(usedCards);
        Collections.shuffle(deck);
        return new ArrayDeque<>(deck);
    }

    public static Card getCardById(int id) {
        return getDeck().stream().filter(card -> card.getId() == id).findFirst().orElse(null);
    }

}
