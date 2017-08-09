package com.example.blackjack.controller;

import com.example.blackjack.entity.Game;
import com.example.blackjack.entity.GameInfo;
import com.example.blackjack.entity.User;
import com.example.blackjack.service.GameService;
import com.example.blackjack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by dima on 28.07.17.
 */
@RestController
@RequestMapping("/users/{userId}/games")
public class GameController {
    @Autowired
    private GameService gameService;
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "")
    private List<GameInfo> findGames(@PathVariable("userId") long id) {
        return gameService.findGamesByUserId(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/active")
    private List<GameInfo> findActiveGames(@PathVariable("userId") long id) {
        return gameService.findActiveGamesByUserId(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/finished")
    private List<GameInfo> findFinishedGames(@PathVariable("userId") long id) {
        return gameService.findFinishedGamesByUserId(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    private GameInfo findGame(@PathVariable("userId") long userId, @PathVariable("id") long gameId) {
        return gameService.findGame(userId, gameId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "")
    private GameInfo startGame(@PathVariable("userId") long userId, @RequestBody Game game) {
        User user = userService.findUser(userId);
        game.setUser(user);
        return gameService.startGame(game);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{gameId}/hit")
    private GameInfo makeHit(@PathVariable("userId") long userId, @PathVariable("gameId") long gameId) {
        return gameService.makeHit(userId, gameId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{gameId}/stand")
    private GameInfo makeStand(@PathVariable("userId") long userId, @PathVariable("gameId") long gameId) {
        return gameService.makeStand(userId, gameId);
    }
}
