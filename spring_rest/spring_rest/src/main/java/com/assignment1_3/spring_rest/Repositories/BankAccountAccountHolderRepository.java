package com.assignment1_3.spring_rest.Repositories;

import com.assignment1_3.spring_rest.Models.Dto.AccountHolderDto;
import com.assignment1_3.spring_rest.Models.Dto.BankAccountDto;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


@Repository
public class BankAccountAccountHolderRepository {

    private Map<Long, HashSet<BankAccountDto>> accountHolderToBankAccount = new HashMap<>();
    private Map<Long, HashSet<AccountHolderDto>> bankAccountToHolder = new HashMap<>();

    public HashSet<BankAccountDto> getBankAccounts(Long accountHolderId) {
        return accountHolderToBankAccount.get(accountHolderId);
    }

    public HashSet<AccountHolderDto> getAccountHolders(Long bankAccountId) {
        return bankAccountToHolder.get(bankAccountId);
    }

    public void addAccountHolder(AccountHolderDto accountHolder) {
        Long id = accountHolder.getId();
        accountHolderToBankAccount.put(id, new HashSet<>());
    }

    public void addBankAccount(BankAccountDto bankAccount) {
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
