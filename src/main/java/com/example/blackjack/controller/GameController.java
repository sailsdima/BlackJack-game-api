package com.example.blackjack.controller;

import com.example.blackjack.entity.Game;
import com.example.blackjack.entity.GameInfo;
import com.example.blackjack.entity.User;
import com.example.blackjack.service.GameService;
import com.example.blackjack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by dima on 28.07.17.
 */
@RestController
public class GameController {
    @Autowired
    private GameService gameService;
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "/users/{id}/games")
    private List<GameInfo> findGames(@PathVariable("id") long id) {
        return gameService.findGamesByUserId(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/{userId}/games/{id}")
    private GameInfo findGame(@PathVariable("id") long id) {
        return gameService.findGame(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/users/{userId}/games")
    private GameInfo startGame(@PathVariable("userId") long userId, @RequestBody Game game) {
        User user = userService.findUser(userId);
        game.setUser(user);
        return gameService.startGame(game);
    }


    @RequestMapping(method = RequestMethod.PUT, value = "/users/{userId}/games/{gameId}/hit")
    private GameInfo makeHit(@PathVariable("userId") long userId, @PathVariable("gameId") long gameId) {
        return gameService.makeHit(gameService.findGame(gameId).getGame());
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/users/{userId}/games/{gameId}/stand")
    private GameInfo makeStand(@PathVariable("userId") long userId, @PathVariable("gameId") long gameId) {
        return gameService.makeStand(gameService.findGame(gameId).getGame());
    }


}
