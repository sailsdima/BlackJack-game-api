package com.example.blackjack.service;

import com.example.blackjack.entity.Transaction;
import com.example.blackjack.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dima on 28.07.17.
 */
@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> findTransactionsByUserId(long userId) {
        List<Transaction> transactions = new ArrayList<>();
        transactions.addAll(transactionRepository.findByUserId(userId));
        return transactions;
    }

    public Transaction findTransaction(long userId, long transactionId) {
        return transactionRepository.findByUserIdAndId(userId, transactionId);
    }

    public Transaction addTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Long removeTransaction(long userId, long transactionId) {
        return transactionRepository.deleteByUserIdAndId(userId, transactionId);
    }

}
