package com.assignment1_3.spring_rest.Models.Request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class BankAccountRequestModel {

    @NotBlank(message = "iban is mandatory")
    private String iban;
    @Min(value = 1, message = "minimal required balance is 1")              // how to check if a user filled this field in?
    private double balance;
    private boolean accountBlocked;                                             // how to check if a user filled this field in?


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
