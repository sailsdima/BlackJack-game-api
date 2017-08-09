package com.example.blackjack.repository;

import com.example.blackjack.entity.Game;
import com.example.blackjack.enumeration.Status;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by dima on 06.08.17.
 */
public interface GameRepository extends CrudRepository<Game,Long>{

    List<Game> findByUserId(long id);
    List<Game> findByUserIdAndStatus(long id, Status status);
    List<Game> findByUserIdAndStatusIsNot(long id, Status status);
    Game findByUserIdAndId(long userId, long gameId);

}
