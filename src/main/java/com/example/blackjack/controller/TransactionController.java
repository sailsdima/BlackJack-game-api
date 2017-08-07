package com.example.blackjack.controller;

import com.example.blackjack.enumeration.TransactionType;
import com.example.blackjack.entity.Transaction;
import com.example.blackjack.entity.User;
import com.example.blackjack.service.TransactionService;
import com.example.blackjack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by dima on 28.07.17.
 */
@RestController
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "/users/{id}/transactions")
    private List<Transaction> findTransactions(@PathVariable("id") long id) {
        return transactionService.findTransactionsByUserId(id);
    }

    @RequestMapping(value = "/users/{userId}/transactions/{id}")
    private Transaction findTransaction(@PathVariable("id") long id) {
        return transactionService.findTransaction(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/users/{userId}/transactions")
    private void addTransaction(@RequestBody Transaction transaction, @PathVariable("userId") long userId) {
        User user = userService.findUser(userId);
        System.out.println(user);
        user.replenishBalance(transaction.getAmount());
        transaction.setUser(user);
        transaction.setDate(new Date());
        transaction.setType(TransactionType.REPLENISHMENT);
        transactionService.addTransaction(transaction);
        userService.updateUser(user);
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/users/{userId}/transactions/{id}")
    private void removeTransaction(@PathVariable long id) {
        transactionService.removeTransaction(id);
    }
}
