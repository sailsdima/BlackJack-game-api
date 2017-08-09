package com.example.blackjack.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by dima on 09.08.17.
 */
class UserTest {

    private User user;

    @BeforeEach
    void setUp() throws Exception {

        user = new User();
        user.setId(1);
        user.setName("TestUser");
        user.setBalance(new BigDecimal("3000"));

    }

    @Test
    void transferBetToDrawBalance() {

        BigDecimal oldBalance = user.getBalance();
        BigDecimal oldDrawBalance = user.getDrawBalance();
        BigDecimal sumToTransfer = new BigDecimal("100");
        user.transferBetToDrawBalance(sumToTransfer);
        assertTrue(user.getBalance().equals(oldBalance.subtract(sumToTransfer))
        && user.getDrawBalance().equals(oldDrawBalance.add(sumToTransfer)));

    }

    @Test
    void decreaseDrawBalance() {
        user.setDrawBalance(new BigDecimal("3000"));
        user.decreaseDrawBalance(new BigDecimal("1000"));
        assertEquals(new BigDecimal("2000"), user.getDrawBalance());
    }

    @Test
    void replenishBalance() {
        BigDecimal oldBalance = user.getBalance();
        BigDecimal replenishment = new BigDecimal("1000");
        user.replenishBalance(replenishment);
        assertEquals(oldBalance.add(replenishment), user.getBalance());
    }

}