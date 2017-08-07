package com.example.blackjack.repository;

import com.example.blackjack.entity.Transaction;
import com.example.blackjack.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by dima on 28.07.17.
 */
public interface TransactionRepository extends CrudRepository<Transaction,Long> {

    List<Transaction> findByUserId(long id);

}
