package com.assignment1_3.spring_rest.Services;

import com.assignment1_3.spring_rest.Models.Dto.AccountHolderDto;
import com.assignment1_3.spring_rest.Models.Dto.BankAccountDto;
import com.assignment1_3.spring_rest.Repositories.AccountHolderRepository;
import com.assignment1_3.spring_rest.Repositories.BankAccountAccountHolderRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;

@Service
public class AccountHolderService {

    private final AccountHolderRepository accountHolderRepository;
    private final BankAccountAccountHolderRepository bankAccountAccountHolderRepository;

    @Autowired
    public AccountHolderService(AccountHolderRepository accountHolderRepository, BankAccountAccountHolderRepository bankAccountAccountHolderRepository) {
        this.accountHolderRepository = accountHolderRepository;
        this.bankAccountAccountHolderRepository = bankAccountAccountHolderRepository;
    }

    public Collection<AccountHolderDto> getAccountHolders() {
        return accountHolderRepository.getAccountHolders();
    }

    public AccountHolderDto getAccountHolderById(Long id) {
        return accountHolderRepository.getAccountHolder(id);
    }

    public AccountHolderDto createAccountHolder(AccountHolderDto accountHolder) {
        accountHolder.generateId();
        AccountHolderDto createdAccountHolder = accountHolderRepository.addAccountHolder(accountHolder);
        bankAccountAccountHolderRepository.addAccountHolder(createdAccountHolder);  // toevoegen aan de many to many map
        return createdAccountHolder;
    }

    public AccountHolderDto updateAccountHolder(Long id, AccountHolderDto accountHolder) {

        AccountHolderDto currAccountHolder = this.getAccountHolderById(id);

        if (currAccountHolder == null) {
            return null;
        }
        else {
            BeanUtils.copyProperties(accountHolder, currAccountHolder);
            return currAccountHolder;
        }
    }

    public AccountHolderDto deleteAccountHolder(Long id) {
        AccountHolderDto accountHolder = this.getAccountHolderById(id);

        if (accountHolder == null) {
            return null;
        }
        else {
            accountHolderRepository.deleteAccountHolder(id);
            return accountHolder;
        }
    }

    public HashSet<BankAccountDto> getBankAccountsByAccountHolderId(Long id) {
        return bankAccountAccountHolderRepository.getBankAccounts(id);
    }


}
