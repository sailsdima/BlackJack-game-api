package com.example.blackjack.service;

import com.example.blackjack.engine.GameEngine;
import com.example.blackjack.entity.Game;
import com.example.blackjack.entity.GameInfo;
import com.example.blackjack.entity.Transaction;
import com.example.blackjack.entity.User;
import com.example.blackjack.enumeration.Status;
import com.example.blackjack.enumeration.TransactionType;
import com.example.blackjack.repository.GameRepository;
import com.example.blackjack.repository.TransactionRepository;
import com.example.blackjack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by dima on 28.07.17.
 */
@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    private GameEngine gameEngine = new GameEngine();

    public List<GameInfo> findGamesByUserId(long userId) {
        List<Game> games = gameRepository.findByUserId(userId);
        return games.stream()
                .map(GameInfo::new)
                .collect(Collectors.toList());
    }

    public GameInfo findGame(long id) {
        return new GameInfo(gameRepository.findOne(id));
    }

    public GameInfo startGame(Game game) {
        gameRepository.save(game);
        GameInfo gameInfo = gameEngine.startGame(game);
        transferBetToDrawBalance(game);
        checkForBlackJack(gameInfo);


        gameRepository.save(new Game(gameInfo));
        return gameInfo;
    }

    public GameInfo makeHit(Game game) {
        GameInfo gameInfo = new GameInfo(game);
        gameInfo = gameEngine.makeHit(gameInfo);

        if (!checkForBlackJack(gameInfo))
            checkForBust(gameInfo);

        gameRepository.save(new Game(gameInfo));
        return gameInfo;
    }

    public GameInfo makeStand(Game game) {
        GameInfo gameInfo = new GameInfo(game);

        gameInfo = gameEngine.addCardsToDealer(gameInfo);

        determineWinner(gameInfo);

        gameRepository.save(new Game(gameInfo));
        return gameInfo;
    }

    private void determineWinner(GameInfo gameInfo) {
        if (checkForBlackJack(gameInfo)) {
            return;
        } else if (checkForBust(gameInfo))
            return;

        int dealerPoints = gameInfo.getDealerPoints();
        int playerPoints = gameInfo.getPlayerPoints();

        if (dealerPoints > playerPoints)
            playerLoose(gameInfo);
        else if (dealerPoints == playerPoints)
            draw(gameInfo);
        else playerWon(gameInfo, false);

    }

    private void transferBetToDrawBalance(Game game) {
        User user = game.getUser();
        user.transferBetToDrawBalance(game.getBet());
        userRepository.save(user);
    }

    private boolean checkForBlackJack(GameInfo gameInfo) {
        if (gameInfo.getPlayerPoints() == 21) {
            gameEngine.addCardsToDealer(gameInfo);
            if (gameInfo.getDealerPoints() != 21) {
                playerWon(gameInfo, true);
            } else {
                draw(gameInfo);
            }
            return true;
        } else if (gameInfo.getDealerPoints() == 21) {
            playerLoose(gameInfo);
            return true;
        }
        return false;
    }

    private boolean checkForBust(GameInfo gameInfo) {
        if (gameInfo.getPlayerPoints() > 21) {
            gameEngine.addCardsToDealer(gameInfo);
            if (gameInfo.getDealerPoints() <= 21)
                playerLoose(gameInfo);
            else
                draw(gameInfo);
        } else if (gameInfo.getDealerPoints() > 21)
            playerWon(gameInfo, false);
        else return false;

        return true;
    }


    private void playerWon(GameInfo gameInfo, boolean blackJack) {
        gameInfo.getGame().setStatus(Status.PLAYER_WON);
        User user = gameInfo.getGame().getUser();
        BigDecimal bet = gameInfo.getGame().getBet();
        BigDecimal moneyWon = (blackJack) ? bet.multiply(new BigDecimal(2.5))
                : bet.multiply(new BigDecimal(2));
        user.decreaseDrawBalance(bet);
        user.replenishBalance(moneyWon);
        userRepository.save(user);

        transactionRepository.save(new Transaction(moneyWon, TransactionType.WINNINGS, user));
    }

    private void draw(GameInfo gameInfo) {
        gameInfo.getGame().setStatus(Status.DRAW);
        User user = gameInfo.getGame().getUser();
        BigDecimal bet = gameInfo.getGame().getBet();
        user.decreaseDrawBalance(bet);
        user.replenishBalance(bet);
        userRepository.save(user);
    }

    private void playerLoose(GameInfo gameInfo) {
        gameInfo.getGame().setStatus(Status.DEALER_WON);
        User user = gameInfo.getGame().getUser();
        BigDecimal bet = gameInfo.getGame().getBet();
        user.decreaseDrawBalance(bet);
        userRepository.save(user);
        transactionRepository.save(new Transaction(bet, TransactionType.LOSS, user));
    }


}
