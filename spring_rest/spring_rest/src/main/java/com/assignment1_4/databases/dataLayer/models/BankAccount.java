package com.assignment1_4.databases.dataLayer.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class BankAccount extends BaseEntity {

    @Column(nullable = false)
    private String iban;
    @Column(nullable = false)
    private double balance;
    @Column(nullable = false)
    private boolean accountBlocked;
    @ManyToMany
    @JoinTable
    private Set<AccountHolder> accountHolders;

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

    public Set<AccountHolder> getAccountHolders() {
        return accountHolders;
    }

    public void setAccountHolders(Set<AccountHolder> accountHolders) {
        this.accountHolders = accountHolders;
    }

    public void addAccountHolder(AccountHolder accountHolder) {
        accountHolders.add(accountHolder);
    }

    public void removeAccountHolder(AccountHolder accountHolder) {
        accountHolders.remove(accountHolder);
    }
}
