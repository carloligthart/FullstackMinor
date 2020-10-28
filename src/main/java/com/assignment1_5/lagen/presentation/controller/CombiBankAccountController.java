package com.assignment1_5.lagen.presentation.controller;

import com.assignment1_5.lagen.business.CombiBankAccountService;
import com.assignment1_5.lagen.dataAcces.entity.CombiBankAccount;
import com.assignment1_5.lagen.exception.BankAccountNotFoundException;
import com.assignment1_5.lagen.presentation.dto.CombiBankAccountRequestDto;
import com.assignment1_5.lagen.presentation.dto.CombiBankAccountResponseDto;
import com.assignment1_5.lagen.presentation.mapper.CombiBankAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/combirekening")
public class CombiBankAccountController {

    private final CombiBankAccountService combiBankAccountService;
    private final CombiBankAccountMapper combiBankAccountMapper;

    @Autowired
    public CombiBankAccountController(CombiBankAccountService combiBankAccountService, CombiBankAccountMapper combiBankAccountMapper) {
        this.combiBankAccountService = combiBankAccountService;
        this.combiBankAccountMapper = combiBankAccountMapper;
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

    @Cacheable("CombiBankAccounts")
    @GetMapping("")
    public ResponseEntity<Collection<CombiBankAccountResponseDto>> getAllCombiBankAccounts() {
       Collection<CombiBankAccountResponseDto> returnValue = new ArrayList<>();
       Collection<CombiBankAccount> retrievedCombiBankAccounts = this.combiBankAccountService.getAllCombiBankAccounts();
       if (retrievedCombiBankAccounts.isEmpty()) {
           throw new RuntimeException("no bankAccounts found");
       }
       else {
           for (CombiBankAccount entity : retrievedCombiBankAccounts) {
               returnValue.add(combiBankAccountMapper.entityToResponse(entity));
           }
       }

       return ResponseEntity.ok(returnValue);
    }

    @Cacheable("CombiBankAccounts")
    @GetMapping("/{iban}")
    public ResponseEntity<CombiBankAccountResponseDto> getCombiBankAccountByIban(@PathVariable("iban") String iban) {
        Optional<CombiBankAccount> optionalCombiBankAccount = combiBankAccountService.getCombiBankAccountByIban(iban);
        CombiBankAccount retrievedCombiBankAccount = optionalCombiBankAccount.orElseThrow( () -> new BankAccountNotFoundException());
        CombiBankAccountResponseDto bankAccountResponseDto = combiBankAccountMapper.entityToResponse(retrievedCombiBankAccount);

        return ResponseEntity.ok(bankAccountResponseDto);

    }

    @CachePut("CombiBankAccounts")
    @PostMapping
    public ResponseEntity<CombiBankAccountResponseDto> createCombiBankAccount(@Valid @RequestBody CombiBankAccountRequestDto combiBankAccountRequestDto) {
        CombiBankAccount combiBankAccountEntity = combiBankAccountMapper.requestToEntity(combiBankAccountRequestDto);
        combiBankAccountService.createCombiBankAccount(combiBankAccountEntity);

        final URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .path("/{id}")
                .buildAndExpand(combiBankAccountEntity.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }
}
