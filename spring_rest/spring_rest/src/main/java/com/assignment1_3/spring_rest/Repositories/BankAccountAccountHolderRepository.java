package com.assignment1_3.spring_rest.Repositories;

import com.assignment1_3.spring_rest.Models.AccountHolderDto;
import com.assignment1_3.spring_rest.Models.BankAccountDto;
import com.assignment1_3.spring_rest.Services.AccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@Repository
public class BankAccountAccountHolderRepository {

    private Map<Long, HashSet<BankAccountDto>> accountHolderToBankAccount = new HashMap<>();      // Use constructor
    private Map<Long, HashSet<AccountHolderDto>> bankAccountToHolder = new HashMap<>();

    public HashSet<BankAccountDto> getBankAccounts(Long accountHolderId) {   //set or hashset as return type?
        return accountHolderToBankAccount.get(accountHolderId);
    }

    public HashSet<AccountHolderDto> getAccountHolders(Long bankAccountId) {   //set or hashset as return type?
        return bankAccountToHolder.get(bankAccountId);
    }

    public void addAccountHolder(AccountHolderDto accountHolder) {    // implementatie checken
        Long id = accountHolder.getId();
        accountHolderToBankAccount.put(id, new HashSet<>());
    }

    public void addBankAccount(BankAccountDto bankAccount) {    // implementatie checken
        Long id = bankAccount.getId();
        bankAccountToHolder.put(id, new HashSet<>());
    }

    public void linkAccountHolder(BankAccountDto bankAccount, AccountHolderDto accountHolder) {
        Long bankAccountId = bankAccount.getId();
        Long accountHolderId = accountHolder.getId();

        this.getAccountHolders(bankAccountId).add(accountHolder);
        this.getBankAccounts(accountHolderId).add(bankAccount);
    }

    public void unLinkAccountHolder(Long bankAccountId, AccountHolderDto accountHolder) {
        this.getBankAccounts(bankAccountId).remove(accountHolder);
    }

}
