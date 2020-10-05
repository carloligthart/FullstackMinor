package com.assignment1_3.spring_rest.Models.Response;

import com.assignment1_3.spring_rest.Models.AccountHolderDto;

import java.util.List;

public class BankAccountResponseModel {

    private Long id;
    private String iban;
    private double balance;
    private boolean accountBlocked;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isAccountBlocked() {
        return accountBlocked;
    }

    public void setAccountBlocked(boolean accountBlocked) {
        this.accountBlocked = accountBlocked;
    }

}