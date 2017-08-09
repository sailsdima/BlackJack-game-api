package com.example.blackjack.repository;

import com.example.blackjack.entity.Transaction;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by dima on 28.07.17.
 */
public interface TransactionRepository extends CrudRepository<Transaction,Long> {

    List<Transaction> findByUserId(long id);
    Transaction findByUserIdAndId(long userId, long transactionId);
    @Transactional
    Long deleteByUserIdAndId(long userId, long transactionId);

}
