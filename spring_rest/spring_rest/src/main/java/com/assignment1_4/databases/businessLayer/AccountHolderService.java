package com.assignment1_4.databases.businessLayer;

import com.assignment1_4.databases.dataLayer.models.AccountHolder;
import com.assignment1_4.databases.dataLayer.models.BankAccount;
import com.assignment1_4.databases.dataLayer.repositories.AccountHolderRepository;
import com.assignment1_4.databases.dataLayer.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class AccountHolderService {

    private final AccountHolderRepository accountHolderRepository;
    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public AccountHolderService(AccountHolderRepository accountHolderRepository, BankAccountRepository bankAccountRepository) {
        this.accountHolderRepository = accountHolderRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    public Collection<AccountHolder> getAccountHolders() {
        return accountHolderRepository.findAll();
    }

    public Optional<AccountHolder> getAccountHolderById(Long id) {
        return accountHolderRepository.findById(id);
    }

    public AccountHolder createAccountHolder(AccountHolder accountHolder) {
        accountHolderRepository.save(accountHolder);
        return accountHolder;
    }

    public void updateAccountHolder(Long id, AccountHolder accountHolder) {
        accountHolder.setId(id);
        accountHolderRepository.save(accountHolder);
    }

    public void deleteAccountHolder(Long id) {
        accountHolderRepository.deleteById(id);
    }

    public Collection<BankAccount> getBankAccountsByAccountHolderId(Long id) {
        return bankAccountRepository.getBankAccountsByAccountHolderId(id);
    }
}
