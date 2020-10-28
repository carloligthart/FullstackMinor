package com.assignment1_5.lagen.dataAcces.repository;

import com.assignment1_5.lagen.dataAcces.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, String> {

    @Query(value = "SELECT b.* FROM bank_account b WHERE b.iban =:iban", nativeQuery = true)
    Optional<BankAccount> findByIban(@Param("iban") String iban);

    @Query(value = "SELECT b.id FROM bank_account b WHERE b.iban =:iban", nativeQuery = true)
    Optional<String> findIdByIban(@Param("iban") String iban);

    @Query(value = "DELETE FROM bank_account b WHERE b.iban =:iban", nativeQuery = true)
    void deleteByIban(@Param("iban") String iban);

}
