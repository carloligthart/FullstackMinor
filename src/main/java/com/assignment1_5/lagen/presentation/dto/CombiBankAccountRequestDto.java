package com.assignment1_5.lagen.presentation.dto;

import javax.validation.constraints.NotNull;

public class CombiBankAccountRequestDto {

    @NotNull(message = "bankAccountA is mandatory")
    private BankAccountRequestDto bankAccountA;
    @NotNull(message = "bankAccountB is mandatory")
    private BankAccountRequestDto bankAccountB;

    public BankAccountRequestDto getBankAccountA() {
        return bankAccountA;
    }

    public void setBankAccountA(BankAccountRequestDto bankAccountA) {
        this.bankAccountA = bankAccountA;
    }

    public BankAccountRequestDto getBankAccountB() {
        return bankAccountB;
    }

    public void setBankAccountB(BankAccountRequestDto bankAccountB) {
        this.bankAccountB = bankAccountB;
    }
}
