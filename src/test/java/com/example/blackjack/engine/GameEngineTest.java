package com.example.blackjack.engine;

import com.example.blackjack.entity.Game;
import com.example.blackjack.entity.GameInfo;
import com.example.blackjack.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by dima on 08.08.17.
 */
public class GameEngineTest {

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

        gameInfo = new GameInfo(game);
        gameEngine = new GameEngine();
    }

    @Test
    private void startGameNotNullTest() throws Exception {
        gameInfo = gameEngine.startGame(game);
        assertNotNull(gameInfo);
    }

    @Test
    private void startGameCardsCountTest() throws Exception {
        gameInfo = gameEngine.startGame(game);
        assertEquals(2, gameInfo.getPlayerCards().size());
        assertEquals(1, gameInfo.getDealerCards().size());
    }

    @Test
    private void makeHitTest() throws Exception {
        gameInfo = gameEngine.startGame(game);
        gameInfo = gameEngine.makeHit(gameInfo);
        assertEquals(3, gameInfo.getPlayerCards().size());
    }

    @Test
    private void addCardsToDealerTest() throws Exception {
        gameInfo = gameEngine.startGame(game);
        gameInfo = gameEngine.addCardsToDealer(gameInfo);
        assertTrue(gameInfo.getDealerPoints() >= 17);
    }


}