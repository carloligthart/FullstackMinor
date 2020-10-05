package com.assignment1_3.spring_rest.Repositories;

import com.assignment1_3.spring_rest.Models.BankAccountDto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;



/*
* Rekening aanmaken
* Rekening blokkeren zonder verwijderen
* Een lijst van alle rekeningen ophalen
* Rekningen moeten in het geheugen worden opgeslagen
* Rekening ophalen
*
* */

@Repository
public class BankAccountRepository {

    private final HashMap<Long, BankAccountDto> bankAccounts;

    public BankAccountRepository() {
        bankAccounts = new HashMap<Long, BankAccountDto>();
    }

    public Collection<BankAccountDto> getBankAccounts() {
        return bankAccounts.values();
    }

    public BankAccountDto getBankAccount(Long id) {
        return bankAccounts.get(id);
    }

    public BankAccountDto addBankAccount(BankAccountDto bankAccount) {
        Long id = bankAccount.getId();
        bankAccounts.put(id, bankAccount);
        return this.getBankAccount(id);
    }

    public BankAccountDto deleteBankAccount(Long id) {
        BankAccountDto returnValue = this.getBankAccount(id);
        bankAccounts.remove(id);
        return returnValue;
    }
}
