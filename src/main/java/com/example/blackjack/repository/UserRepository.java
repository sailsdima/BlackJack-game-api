package com.example.blackjack.repository;

import com.example.blackjack.entity.User;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by dima on 28.07.17.
 */
public interface UserRepository extends CrudRepository<User,Long> {

    @Transactional
    Long deleteById(long id);

}
