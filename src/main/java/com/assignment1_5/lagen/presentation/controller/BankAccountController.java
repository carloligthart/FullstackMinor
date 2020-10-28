package com.assignment1_5.lagen.presentation.controller;

import com.assignment1_5.lagen.business.BankAccountService;
import com.assignment1_5.lagen.dataAcces.entity.BankAccount;
import com.assignment1_5.lagen.exception.BankAccountNotFoundException;
import com.assignment1_5.lagen.presentation.dto.BankAccountRequestDto;
import com.assignment1_5.lagen.presentation.dto.BankAccountResponseDto;
import com.assignment1_5.lagen.presentation.mapper.BankAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/rekening")
public class BankAccountController {

    private final BankAccountService bankAccountService;
    private final BankAccountMapper bankAccountMapper;

    @Autowired
    public BankAccountController(BankAccountService bankAccountService, BankAccountMapper bankAccountMapper) {
        this.bankAccountService = bankAccountService;
        this.bankAccountMapper = bankAccountMapper;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @Cacheable("bankAccounts")
    @GetMapping("")
    public ResponseEntity<Collection<BankAccountResponseDto>> getAllBankAccounts() {
        Collection<BankAccountResponseDto> returnValue = new ArrayList<>();
        Collection<BankAccount> retrievedBankAccounts = this.bankAccountService.getAllBankAccounts();
        if (retrievedBankAccounts.isEmpty()) {
            throw new RuntimeException("no bankAccounts found");
        }
        else {
            for (BankAccount entity : retrievedBankAccounts) {
                returnValue.add(bankAccountMapper.entityToResponse(entity));
            }
        }

        return ResponseEntity.ok(returnValue);
    }

    @Cacheable("bankAccounts")
    @GetMapping("/{iban}")
    public ResponseEntity<BankAccountResponseDto> getBankAccountByIban(@PathVariable("iban") String iban) {
        Optional<BankAccount> optionalBankAccount = bankAccountService.getBankAccountByIban(iban);
        BankAccount retrievedBankAccount = optionalBankAccount.orElseThrow( () -> new BankAccountNotFoundException());
        BankAccountResponseDto bankAccountResponseDto = bankAccountMapper.entityToResponse(retrievedBankAccount);
        return ResponseEntity.ok(bankAccountResponseDto);
    }

    @CachePut(value = "bankAccounts")
    @PostMapping
    public ResponseEntity<BankAccountResponseDto> createBankAccount(@Valid @RequestBody BankAccountRequestDto bankAccountRequestDto) {
        BankAccount bankAccountEntity = bankAccountMapper.requestToEntity(bankAccountRequestDto);
        bankAccountService.createBankAccount(bankAccountEntity);

        final URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .path("/{iban}")
                .buildAndExpand(bankAccountEntity.getIban())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @CachePut(value = "bankAccounts")
    @PutMapping("/{iban}")
    public ResponseEntity<BankAccountResponseDto> updateBankAccountByIban(@PathVariable("iban") String iban, @Valid @RequestBody BankAccountRequestDto bankAccountRequestDto) {
        BankAccount bankAccountEntity = bankAccountMapper.requestToEntity(bankAccountRequestDto);
        bankAccountService.updateBankAccountByIban(bankAccountEntity, iban);

        return ResponseEntity.noContent().build();
    }

    @CacheEvict(value = "bankAccounts")
    @DeleteMapping("/{iban}")
    public ResponseEntity<BankAccountResponseDto> deleteBankAccountByIban(@PathVariable("iban") String iban) {
        this.bankAccountService.deleteBankAccountByIban(iban);

        return ResponseEntity.noContent().build();
    }

}
