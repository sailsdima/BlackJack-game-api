package com.example.blackjack.entity;

import com.example.blackjack.enumeration.TransactionType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dima on 04.08.17.
 */

@Entity
public class Transaction {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    private BigDecimal amount;
    private BigDecimal currentBalance;
    private Date date = new Date();
    private TransactionType transactionType;

    @ManyToOne
    private User user;

    public Transaction() {
    }

    public Transaction(BigDecimal amount, TransactionType transactionType, User user) {
        this.amount = amount;
        this.transactionType = transactionType;
        this.currentBalance = user.getBalance();
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
