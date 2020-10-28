package com.assignment1_5.lagen.business;

import com.assignment1_5.lagen.dataAcces.entity.BankAccount;
import com.assignment1_5.lagen.dataAcces.entity.CombiBankAccount;
import com.assignment1_5.lagen.dataAcces.repository.BankAccountRepository;
import com.assignment1_5.lagen.dataAcces.repository.CombiBankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CombiBankAccountService {

    private final CombiBankAccountRepository combiBankAccountRepository;
    private final BankAccountRepository bankAccountRepository;
    private final BankAccountService bankAccountService;

    @Autowired
    public CombiBankAccountService(CombiBankAccountRepository combiBankAccountRepository, BankAccountRepository bankAccountRepository, BankAccountService bankAccountService) {
        this.combiBankAccountRepository = combiBankAccountRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.bankAccountService = bankAccountService;
    }

    public Collection<CombiBankAccount> getAllCombiBankAccounts() {
        return combiBankAccountRepository.findAll();
    }

    public Optional<CombiBankAccount> getCombiBankAccountByIban(String iban) {
        return combiBankAccountRepository.findByIban(iban);

    }

    @Transactional(readOnly = false)
    public void createCombiBankAccount(CombiBankAccount combiBankAccount) {
        combiBankAccountRepository.save(combiBankAccount);
        combiBankAccount.setCombiBankAccountNumber(String.format("%08d", Integer.parseInt(combiBankAccount.getId())));
        combiBankAccount.setIban("NL00COMBI" + String.format("%08d", Integer.parseInt(combiBankAccount.getId())));

        BankAccount bankAccountA = createSubBankAccount(combiBankAccount.getBankAccountA());
        BankAccount bankAccountB = createSubBankAccount(combiBankAccount.getBankAccountB());

        combiBankAccount.setBankAccountA(bankAccountA);
        combiBankAccount.setBankAccountB(bankAccountB);

    }

    private BankAccount createSubBankAccount(BankAccount bankAccount) {
        bankAccountRepository.save(bankAccount);
        bankAccount.setBankAccountNumber(String.format("%08d", Integer.parseInt(bankAccount.getId())));
        bankAccount.setIban("NL99BANK" + String.format("%08d", Integer.parseInt(bankAccount.getId())));
        return bankAccountRepository.save(bankAccount);
    }
}
