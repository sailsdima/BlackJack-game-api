package com.example.blackjack.repository;

import com.example.blackjack.entity.Game;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by dima on 06.08.17.
 */
public interface GameRepository extends CrudRepository<Game,Long>{

    List<Game> findByUserId(long id);

}
