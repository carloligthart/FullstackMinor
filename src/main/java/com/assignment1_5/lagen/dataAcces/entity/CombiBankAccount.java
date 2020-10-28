package com.assignment1_5.lagen.dataAcces.entity;

import javax.persistence.*;

@Entity
@Table
public class CombiBankAccount extends BaseEntity {

    @Column // no constraints because of the way the iban is generated (requires the entry to be flushed before its generated)
    private String combiBankAccountNumber;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "bank_account_A_id")
    private BankAccount bankAccountA;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "bank_account_B_id")
    private BankAccount bankAccountB;

    public String getCombiBankAccountNumber() {
        return combiBankAccountNumber;
    }

    public void setCombiBankAccountNumber(String combiBankAccountNumber) {
        this.combiBankAccountNumber = combiBankAccountNumber;
    }

    public BankAccount getBankAccountA() {
        return bankAccountA;
    }

    public void setBankAccountA(BankAccount bankAccountA) {
        this.bankAccountA = bankAccountA;
    }

    public BankAccount getBankAccountB() {
        return bankAccountB;
    }

    public void setBankAccountB(BankAccount bankAccountB) {
        this.bankAccountB = bankAccountB;
    }
}
