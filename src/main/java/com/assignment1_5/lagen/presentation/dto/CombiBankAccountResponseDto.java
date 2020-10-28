package com.assignment1_5.lagen.presentation.dto;

public class CombiBankAccountResponseDto {

    private String id;
    private String iban;
    private BankAccountResponseDto bankAccountA;
    private BankAccountResponseDto bankAccountB;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public BankAccountResponseDto getBankAccountA() {
        return bankAccountA;
    }

    public void setBankAccountA(BankAccountResponseDto bankAccountA) {
        this.bankAccountA = bankAccountA;
    }

    public BankAccountResponseDto getBankAccountB() {
        return bankAccountB;
    }

    public void setBankAccountB(BankAccountResponseDto bankAccountB) {
        this.bankAccountB = bankAccountB;
    }
}
