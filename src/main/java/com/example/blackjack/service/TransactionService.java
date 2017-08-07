package com.example.blackjack.service;

import com.example.blackjack.entity.Transaction;
import com.example.blackjack.entity.User;
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

    public List<Transaction> findTransactionsByUserId(long userId){
        List<Transaction> transactions = new ArrayList<>();
        transactionRepository.findByUserId(userId).forEach(user -> transactions.add(user));
        return transactions;
    }

    public Transaction findTransaction(long id){
        return transactionRepository.findOne(id);
    }

    public void addTransaction(Transaction transaction){
        transactionRepository.save(transaction);
    }

    public void removeTransaction(long id){
        transactionRepository.delete(id);
    }

}
