package com.assignment1_3.spring_rest.Services;

import com.assignment1_3.spring_rest.Models.Dto.AccountHolderDto;
import com.assignment1_3.spring_rest.Models.Dto.BankAccountDto;
import com.assignment1_3.spring_rest.Repositories.BankAccountAccountHolderRepository;
import com.assignment1_3.spring_rest.Repositories.BankAccountRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;

@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final BankAccountAccountHolderRepository bankAccountAccountHolderRepository;
    private final AccountHolderService accountHolderService;

    @Autowired // Check if this is done correctly
    public BankAccountService(BankAccountRepository bankAccountRepository, BankAccountAccountHolderRepository bankAccountAccountHolderRepository,AccountHolderService accountHolderService) {
        this.bankAccountRepository = bankAccountRepository;
        this.bankAccountAccountHolderRepository = bankAccountAccountHolderRepository;
        this.accountHolderService = accountHolderService;
    }

    public Collection<BankAccountDto> getBankAccounts() {
        return bankAccountRepository.getBankAccounts();
    }

    public BankAccountDto getBankAccountById(Long id) {
        return bankAccountRepository.getBankAccount(id);
    }

    public BankAccountDto createBankAccount(BankAccountDto bankAccount) {
        bankAccount.generateId();
        BankAccountDto createdBankAccount = bankAccountRepository.addBankAccount(bankAccount);
        bankAccountAccountHolderRepository.addBankAccount(createdBankAccount);
        return createdBankAccount;
    }

    public BankAccountDto updateBankAccount(Long id, BankAccountDto bankAccount) {

        BankAccountDto currBankAccount = this.getBankAccountById(id);

        if (currBankAccount == null) {
            return null;
        }
        else {
            BeanUtils.copyProperties(bankAccount, currBankAccount);
            return currBankAccount;
        }
    }

    public BankAccountDto deleteBankAccount(Long id) {
        BankAccountDto bankAccount = this.getBankAccountById(id);

        if (bankAccount == null) {
            return null;
        }
        else {
            bankAccountRepository.deleteBankAccount(id);
            return bankAccount;
        }
    }

    public BankAccountDto blockBankAccount(Long id) {
        BankAccountDto bankAccount = this.getBankAccountById(id);

        if (bankAccount == null) {
            return null;
        }
        else {
            bankAccount.setAccountBlocked(true);
            return bankAccount;
        }
    }

    public BankAccountDto unBlockBankAccount(Long id) {
        BankAccountDto bankAccount = this.getBankAccountById(id);

        if (bankAccount == null) {
            return null;
        }
        else {
            bankAccount.setAccountBlocked(false);
            return bankAccount;
        }
    }

    public void linkAccountHolder(Long bankAccountId, Long accountHolderId) {
        AccountHolderDto accountHolder = accountHolderService.getAccountHolderById(accountHolderId);
        BankAccountDto bankAccount = this.getBankAccountById(bankAccountId);
        bankAccountAccountHolderRepository.linkAccountHolder(bankAccount, accountHolder);
    }

    public void unLinkAccountHolder(Long bankAccountId, Long accountHolderId) {
        AccountHolderDto accountHolder = accountHolderService.getAccountHolderById(accountHolderId);
        bankAccountAccountHolderRepository.unLinkAccountHolder(bankAccountId,accountHolder);
    }

    public HashSet<AccountHolderDto> getAccountHoldersByBankAccountId(Long id) {
        return bankAccountAccountHolderRepository.getAccountHolders(id);
    }

}
