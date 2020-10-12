package com.assignment1_4.databases.dataLayer.repositories;

import com.assignment1_4.databases.dataLayer.models.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    @Query(value = "SELECT ba.* FROM BANK_ACCOUNT_ACCOUNT_HOLDERS baah INNER JOIN BANK_ACCOUNT ba ON ba.ID = baah.BANK_ACCOUNTS_ID where baah.ACCOUNT_HOLDERS_ID =:accountHolderId", nativeQuery = true)
    Collection<BankAccount> getBankAccountsByAccountHolderId(long accountHolderId);

}
