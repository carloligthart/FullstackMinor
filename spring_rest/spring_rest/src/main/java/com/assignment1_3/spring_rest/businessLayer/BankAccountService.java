package com.assignment1_3.spring_rest.businessLayer;

import com.assignment1_3.spring_rest.exceptions.AccountHolderNotFoundException;
import com.assignment1_3.spring_rest.exceptions.BankAccountNotFoundException;
import com.assignment1_3.spring_rest.dataLayer.models.AccountHolder;
import com.assignment1_3.spring_rest.dataLayer.models.BankAccount;
import com.assignment1_3.spring_rest.dataLayer.repositories.AccountHolderRepository;
import com.assignment1_3.spring_rest.dataLayer.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final AccountHolderService accountHolderService;
    private final AccountHolderRepository accountHolderRepository;

    @Autowired
    public BankAccountService(BankAccountRepository bankAccountRepository, AccountHolderService accountHolderService, AccountHolderRepository accountHolderRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.accountHolderService = accountHolderService;
        this.accountHolderRepository = accountHolderRepository;
    }

    public Collection<BankAccount> getBankAccounts() {
        return bankAccountRepository.findAll();
    }

    public Optional<BankAccount> getBankAccountById(Long id) {
        return bankAccountRepository.findById(id);
    }

    public BankAccount createBankAccount(BankAccount bankAccount) {
        bankAccountRepository.save(bankAccount);
        return bankAccount;
    }

    public void updateBankAccount(Long id, BankAccount bankAccount) {
        bankAccount.setId(id);
        bankAccountRepository.save(bankAccount);
    }

    public void deleteBankAccount(Long id) {
        bankAccountRepository.deleteById(id);
    }

    public void blockBankAccount(Long id) {
        Optional<BankAccount> optionalBankAccount = this.getBankAccountById(id);
        BankAccount bankAccount = optionalBankAccount.orElseThrow( () -> new BankAccountNotFoundException(id) );
        bankAccount.setAccountBlocked(true);
        bankAccountRepository.save(bankAccount);
    }

    public void unBlockBankAccount(Long id) {
        Optional<BankAccount> optionalBankAccount = this.getBankAccountById(id);
        BankAccount bankAccount = optionalBankAccount.orElseThrow( () -> new BankAccountNotFoundException(id) );
        bankAccount.setAccountBlocked(false);
        bankAccountRepository.save(bankAccount);
    }

    public void linkAccountHolder(Long bankAccountId, Long accountHolderId) {
        Optional<BankAccount> optionalBankAccount = this.getBankAccountById(bankAccountId);
        BankAccount bankAccount = optionalBankAccount.orElseThrow( () -> new BankAccountNotFoundException(bankAccountId) );
        Optional<AccountHolder> optionalAccountHolder = accountHolderService.getAccountHolderById(accountHolderId);
        AccountHolder accountHolder = optionalAccountHolder.orElseThrow( () -> new AccountHolderNotFoundException(accountHolderId) );
        bankAccount.addAccountHolder(accountHolder);
        bankAccountRepository.save(bankAccount);
    }

    public void unLinkAccountHolder(Long bankAccountId, Long accountHolderId) {
        Optional<BankAccount> optionalBankAccount = this.getBankAccountById(bankAccountId);
        BankAccount bankAccount = optionalBankAccount.orElseThrow( () -> new BankAccountNotFoundException(bankAccountId) );
        Optional<AccountHolder> optionalAccountHolder = accountHolderService.getAccountHolderById(accountHolderId);
        AccountHolder accountHolder = optionalAccountHolder.orElseThrow( () -> new AccountHolderNotFoundException(accountHolderId) );
        bankAccount.removeAccountHolder(accountHolder);
        bankAccountRepository.save(bankAccount);
    }

    public Collection<AccountHolder> getAccountHoldersByBankAccountId(Long id) {
        return accountHolderRepository.getAccountHoldersByBankAccountId(id);
    }

}
