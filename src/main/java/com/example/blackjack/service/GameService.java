package com.example.blackjack.service;

import com.example.blackjack.engine.GameEngine;
import com.example.blackjack.entity.Game;
import com.example.blackjack.entity.GameInfo;
import com.example.blackjack.entity.User;
import com.example.blackjack.entity.Transaction;
import com.example.blackjack.enumeration.Status;
import com.example.blackjack.enumeration.TransactionType;
import com.example.blackjack.exception.BetPlacedException;
import com.example.blackjack.exception.ForbiddenGameAccessException;
import com.example.blackjack.exception.GameIsAlreadyOverException;
import com.example.blackjack.repository.GameRepository;
import com.example.blackjack.repository.TransactionRepository;
import com.example.blackjack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<GameInfo> findActiveGamesByUserId(long id) {
        List<Game> games = gameRepository.findByUserIdAndStatus(id, Status.IN_PROGRESS);
        return games.stream()
                .map(GameInfo::new)
                .collect(Collectors.toList());
    }

    public List<GameInfo> findFinishedGamesByUserId(long id) {
        List<Game> games = gameRepository.findByUserIdAndStatusIsNot(id, Status.IN_PROGRESS);
        return games.stream()
                .map(GameInfo::new)
                .collect(Collectors.toList());
    }

    public GameInfo findGame(long userId, long gameId) {
        Game game = gameRepository.findByUserIdAndId(userId, gameId);

        return new GameInfo(game);
    }

    public GameInfo startGame(Game game) {
        if(game.getBet() == null || game.getBet().signum() < 0)
            throw new BetPlacedException("Bet is null or negative.");
        GameInfo gameInfo = gameEngine.startGame(game);

        gameRepository.save(game);
        transferBetToDrawBalance(game);
        checkForBlackJack(gameInfo);

        gameRepository.save(new Game(gameInfo));
        return gameInfo;
    }

    public GameInfo makeHit(long userId, long gameId) {
        GameInfo gameInfo = findGame(userId, gameId);
        checkGameIsPlayable(userId, gameId, gameInfo);

        gameInfo = gameEngine.makeHit(gameInfo);

        if (!checkForBlackJack(gameInfo))
            checkForBust(gameInfo);

        gameRepository.save(new Game(gameInfo));
        return gameInfo;
    }

    public GameInfo makeStand(long userId, long gameId) {
        GameInfo gameInfo = findGame(userId, gameId);

        checkGameIsPlayable(userId, gameId, gameInfo);

        gameInfo = gameEngine.addCardsToDealer(gameInfo);

        determineWinner(gameInfo);

        gameRepository.save(new Game(gameInfo));
        return gameInfo;
    }

    private void checkGameIsPlayable(long userId, long gameId, GameInfo gameInfo) {
        if (gameInfo == null)
            throw new ForbiddenGameAccessException("User with id: " + userId + "" +
                    " cannot get an access to game with id: " + gameId);

        if(!gameInfo.getGame().getStatus().equals(Status.IN_PROGRESS)){
            throw new GameIsAlreadyOverException("Game with id: " + gameId + " is already over");
        }

    }

    private void determineWinner(GameInfo gameInfo) {
        if (checkForBlackJack(gameInfo) || checkForBust(gameInfo))
            return;

        int dealerPoints = gameInfo.getDealerPoints();
        int playerPoints = gameInfo.getPlayerPoints();

        if (dealerPoints > playerPoints)
            playerLoose(gameInfo);
        else if (dealerPoints == playerPoints)
            draw(gameInfo);
        else playerWon(gameInfo, false);

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

    private boolean gameIsInProgress(Game game) {
        return game.getStatus().equals(Status.IN_PROGRESS);
    }


    private void playerWon(GameInfo gameInfo, boolean blackJack) {
        if (!gameIsInProgress(gameInfo.getGame()))
            return;

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
        if (!gameIsInProgress(gameInfo.getGame()))
            return;

        gameInfo.getGame().setStatus(Status.DRAW);
        User user = gameInfo.getGame().getUser();
        BigDecimal bet = gameInfo.getGame().getBet();
        user.decreaseDrawBalance(bet);
        user.replenishBalance(bet);
        userRepository.save(user);
    }

    private void playerLoose(GameInfo gameInfo) {
        if (!gameIsInProgress(gameInfo.getGame()))
            return;

        gameInfo.getGame().setStatus(Status.DEALER_WON);
        User user = gameInfo.getGame().getUser();
        BigDecimal bet = gameInfo.getGame().getBet();
        user.decreaseDrawBalance(bet);
        userRepository.save(user);
        transactionRepository.save(new Transaction(bet, TransactionType.LOSS, user));
    }

    private void transferBetToDrawBalance(Game game) {
        if (!gameIsInProgress(game))
            return;

        User user = game.getUser();
        user.transferBetToDrawBalance(game.getBet());
        userRepository.save(user);
    }
}
