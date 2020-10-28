package com.assignment1_5.lagen.dataAcces.repository;

import com.assignment1_5.lagen.dataAcces.entity.BankAccount;
import com.assignment1_5.lagen.dataAcces.entity.CombiBankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CombiBankAccountRepository extends JpaRepository<CombiBankAccount, String> {

    @Query(value = "SELECT b.* FROM combi_bank_account b WHERE b.iban =:iban", nativeQuery = true)
    Optional<CombiBankAccount> findByIban(@Param("iban") String iban);

}
