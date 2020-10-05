package com.assignment1_3.spring_rest.Models;

import java.util.List;

public class AccountHolderDto {

    private static Long idCounter = Long.valueOf(0);

    private Long id;
    private String firstName;
    private String lastName;
    private List<BankAccountDto> bankAccountDtos;


    public Long getId() {
        return id;
    }

    public void generateId() {
        this.id = idCounter + 1;
        idCounter++;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<BankAccountDto> getBankAccountDtos() {
        return bankAccountDtos;
    }

    public void setBankAccountDtos(List<BankAccountDto> bankAccountDtos) {
        this.bankAccountDtos = bankAccountDtos;
    }
}
