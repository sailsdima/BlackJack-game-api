package com.example.blackjack.controller;

import com.example.blackjack.entity.Game;
import com.example.blackjack.entity.GameInfo;
import com.example.blackjack.entity.User;
import com.example.blackjack.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by dima on 28.07.17.
 */
@RestController
public class GameController {
    @Autowired
    private GameService gameService;

    @RequestMapping(method = RequestMethod.GET, value = "/users/{id}/games")
    private List<GameInfo> findGames(@PathVariable("id") long id) {
        return gameService.findGamesByUserId(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/{userId}/games/{id}")
    private GameInfo findGame(@PathVariable("id") long id) {
        return gameService.findGame(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/users/{userId}/games/")
    private GameInfo startGame(@PathVariable("userId") long userId, @RequestBody BigDecimal bet) {
        User user = new User();
        user.setId(userId);
        Game game = new Game(user,bet);
        return gameService.addGame(game);
    }


    @RequestMapping(method = RequestMethod.PUT, value = "/users/{userId}/games/{gameId}/hit")
    private GameInfo makeHit(@PathVariable("userId") long userId, @PathVariable("gameId") long gameId) {

        return null;
    }



}
