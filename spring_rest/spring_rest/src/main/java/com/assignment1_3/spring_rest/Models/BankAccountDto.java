package com.assignment1_3.spring_rest.Models;

import java.util.List;

public class BankAccountDto {

    private static Long idCounter = Long.valueOf(0);

    private Long id;
    private String iban;
    private double balance;
    private boolean accountBlocked;
    private List<AccountHolderDto> accountHolderDtos;

    public Long getId() {
        return id;
    }

    public void generateId() {
        this.id = idCounter + 1;
        idCounter++;
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

    public List<AccountHolderDto> getAccountHolders() {
        return accountHolderDtos;
    }

    public void setAccountHolders(List<AccountHolderDto> accountHolderDtos) {
        this.accountHolderDtos = accountHolderDtos;
    }
}
