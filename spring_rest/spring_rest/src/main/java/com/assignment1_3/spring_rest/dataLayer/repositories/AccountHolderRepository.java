package com.assignment1_3.spring_rest.dataLayer.repositories;

import com.assignment1_3.spring_rest.dataLayer.models.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface AccountHolderRepository extends JpaRepository<AccountHolder, Long> {

    @Query(value = "SELECT ah.* FROM BANK_ACCOUNT_ACCOUNT_HOLDERS  baah JOIN ACCOUNT_HOLDER AH ON ah.ID = baah.ACCOUNT_HOLDERS_ID where  baah.BANK_ACCOUNTS_ID =:bankAccountId ", nativeQuery = true)
    Collection<AccountHolder> getAccountHoldersByBankAccountId(long bankAccountId);
}
