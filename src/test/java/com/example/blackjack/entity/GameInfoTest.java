package com.example.blackjack.entity;

import com.example.blackjack.engine.GameEngine;
import com.example.blackjack.enumeration.Rank;
import com.example.blackjack.enumeration.Suit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by dima on 08.08.17.
 */
class GameInfoTest {

    private Game game;
    private GameInfo gameInfo;
    private GameEngine gameEngine;

    @BeforeEach
    private void setUp() throws Exception {

        User user = new User();
        user.setId(1);
        user.setName("UserTest");

        game = new Game();
        game.setId(1);
        game.setBet(new BigDecimal("100"));
        game.setUser(user);

        gameEngine = new GameEngine();
        gameInfo = gameEngine.startGame(game);
    }

    @Test
    void getPlayerPointsTest() {
        List<Card> cardList = new ArrayList<>();
        cardList.addAll(Arrays.asList(
                new Card(0, Rank.TWO, Suit.CLUBS),
                new Card(0, Rank.TWO, Suit.CLUBS),
                new Card(0, Rank.ACE, Suit.CLUBS)));
        gameInfo.setPlayerCards(cardList);
        assertEquals(15, gameInfo.getPlayerPoints());
    }

    @Test
    void getDealerPointsTest() {
        List<Card> cardList = new ArrayList<>(Arrays.asList(
                new Card(0, Rank.FIVE, Suit.CLUBS),
                new Card(0, Rank.SIX, Suit.CLUBS),
                new Card(0, Rank.ACE, Suit.CLUBS)));
        gameInfo.setDealerCards(cardList);
        assertEquals(12, gameInfo.getDealerPoints());
    }

    @Test
    void addPlayerCardTest() {
        Card card = new Card(0, Rank.FIVE, Suit.CLUBS);
        gameInfo.addPlayerCard(card);
        assertTrue(gameInfo.getPlayerCards().contains(card));
    }

    @Test
    void addDealerCardTest() {
        Card card = new Card(0, Rank.FIVE, Suit.CLUBS);
        gameInfo.addDealerCard(card);
        assertTrue(gameInfo.getDealerCards().contains(card));
    }

}