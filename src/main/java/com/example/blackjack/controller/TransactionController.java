package com.example.blackjack.controller;

import com.example.blackjack.entity.User;
import com.example.blackjack.enumeration.TransactionType;
import com.example.blackjack.entity.Transaction;
import com.example.blackjack.service.TransactionService;
import com.example.blackjack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by dima on 28.07.17.
 */
@RestController
@RequestMapping("/users/{userId}/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "")
    private List<Transaction> findTransactions(@PathVariable("userId") long id) {
        return transactionService.findTransactionsByUserId(id);
    }

    @RequestMapping(value = "/{id}")
    private Transaction findTransaction(@PathVariable("userId") long userId, @PathVariable("id") long id) {
        return transactionService.findTransaction(userId, id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "")
    private Transaction addTransaction(@RequestBody Transaction transaction, @PathVariable("userId") long userId) {
        User user = userService.findUser(userId);
        user.replenishBalance(transaction.getAmount());
        transaction.setUser(user);
        transaction.setCurrentBalance(user.getBalance());
        transaction.setType(TransactionType.REPLENISHMENT);
        userService.updateUser(user);
        return transactionService.addTransaction(transaction);
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    private Long removeTransaction(@PathVariable("userId") long userId, @PathVariable long id) {
        return transactionService.removeTransaction(userId, id);
    }
}
