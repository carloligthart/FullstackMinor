package com.assignment1_5.lagen.business;

import com.assignment1_5.lagen.dataAcces.entity.BankAccount;
import com.assignment1_5.lagen.dataAcces.repository.BankAccountRepository;
import com.assignment1_5.lagen.exception.BankAccountNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

//    public String getIdByIban(String iban) {
//        Optional<String> optionalId = bankAccountRepository.findIdByIban(iban);
//        String retrievedId = optionalId.orElseThrow( () -> new RuntimeException( "Id not found") );
//        return retrievedId;
//    }

    public Collection<BankAccount> getAllBankAccounts() {
        return bankAccountRepository.findAll();
    }

    public Optional<BankAccount> getBankAccountByIban(String iban) {
        return bankAccountRepository.findByIban(iban);
    }

    @Transactional(readOnly = false)
    public void createBankAccount(BankAccount bankAccount) {
        bankAccountRepository.save(bankAccount);
        bankAccount.setBankAccountNumber(String.format("%08d", Integer.parseInt(bankAccount.getId())));
        bankAccount.setIban("NL99BANK" + String.format("%08d", Integer.parseInt(bankAccount.getId())));
    }

    @Transactional(readOnly = false)
    public void updateBankAccountByIban(BankAccount bankAccount, String iban) {
        Optional<BankAccount> optionalBankAccount = this.getBankAccountByIban(iban);
        BankAccount retrievedBankAccount = optionalBankAccount.orElseThrow( () -> new BankAccountNotFoundException());

        bankAccount.setIban(retrievedBankAccount.getIban());
        bankAccount.setBankAccountNumber(retrievedBankAccount.getBankAccountNumber());

        bankAccountRepository.save(bankAccount);

    }

//    public void deleteBankAccountByIban(String iban) {
//        String id = this.getIdByIban(iban);
//        bankAccountRepository.deleteById(id);
//    }

    @Transactional(readOnly = false)
    public void deleteBankAccountByIban(String iban) {
        bankAccountRepository.deleteByIban(iban);
    }

}
