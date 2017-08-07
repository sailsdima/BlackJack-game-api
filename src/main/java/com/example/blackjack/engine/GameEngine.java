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


    public GameInfo startGame(Game game){

        GameInfo gameStatus = new GameInfo(game);

        if(game.getUser().getBalance().compareTo(game.getBet()) > 0){
            gameStatus.getGame().setStatus(Status.CANCELLED);
            return gameStatus;
        }

        gameStatus.setDeck(getShuffledDeck());
        gameStatus.addPlayerCard(gameStatus.getDeck().poll());
        gameStatus.addPlayerCard(gameStatus.getDeck().poll());

        gameStatus.addDealerCard(gameStatus.getDeck().poll());
        gameStatus.setHiddenCard(gameStatus.getDeck().poll());

        return gameStatus;
    }


    private static List<Card> getDeck(){
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

    public static Card getCardById(int id){
        return getDeck().stream().filter(card -> card.getId() == id).findFirst().orElse(null);
    }
}
