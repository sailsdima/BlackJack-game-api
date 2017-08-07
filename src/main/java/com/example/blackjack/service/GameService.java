package com.example.blackjack.service;

import com.example.blackjack.engine.GameEngine;
import com.example.blackjack.entity.Game;
import com.example.blackjack.entity.GameInfo;
import com.example.blackjack.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by dima on 28.07.17.
 */
@Transactional
@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    private GameEngine gameEngine = new GameEngine();

    public List<GameInfo> findGamesByUserId(long userId){
        List<Game> games = gameRepository.findByUserId(userId);
        return games.stream()
                .map(GameInfo::new)
                .collect(Collectors.toList());
    }

    public GameInfo findGame(long id){
        return new GameInfo(gameRepository.findOne(id));
    }

    public GameInfo addGame(Game game){
        gameRepository.save(game);

        return new GameInfo(game);
    }
}
