package com.assignment1_3.spring_rest.Repositories;

import com.assignment1_3.spring_rest.Models.Dto.AccountHolderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;

@Repository
public class AccountHolderRepository {

    private final HashMap<Long, AccountHolderDto> accountHolders;

    @Autowired
    public AccountHolderRepository() {
        accountHolders = new HashMap<Long, AccountHolderDto>();
    }

    public Collection<AccountHolderDto> getAccountHolders() {
        return accountHolders.values();
    }

    public AccountHolderDto getAccountHolder(Long id) {
        return accountHolders.get(id);
    }

    public AccountHolderDto addAccountHolder(AccountHolderDto accountHolder) {
        Long id = accountHolder.getId();
        accountHolders.put(id, accountHolder);
        return this.getAccountHolder(id);
    }

    public AccountHolderDto deleteAccountHolder(Long id) {
        AccountHolderDto returnValue = this.getAccountHolder(id);
        accountHolders.remove(id);
        return returnValue;
    }
}
