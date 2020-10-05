package com.assignment1_3.spring_rest.Controllers;

import com.assignment1_3.spring_rest.Models.AccountHolderDto;
import com.assignment1_3.spring_rest.Models.BankAccountDto;
import com.assignment1_3.spring_rest.Models.Request.AccountHolderRequestModel;
import com.assignment1_3.spring_rest.Models.Response.AccountHolderResponseModel;
import com.assignment1_3.spring_rest.Models.Response.BankAccountResponseModel;
import com.assignment1_3.spring_rest.Repositories.BankAccountAccountHolderRepository;
import com.assignment1_3.spring_rest.Services.AccountHolderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLOutput;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/holders")
public class AccountHolderController {

    private final AccountHolderService accountHolderService;

    @Autowired
    public AccountHolderController(AccountHolderService accountHolderService) {
        this.accountHolderService = accountHolderService;
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

    @GetMapping("")
    public ResponseEntity<Collection<AccountHolderResponseModel>> getBankAccounts() {

        CacheControl cacheControl = CacheControl.maxAge(60, TimeUnit.SECONDS);

        Collection<AccountHolderResponseModel> returnValue = new ArrayList<>();
        Collection<AccountHolderDto> retrievedAccountHolders = accountHolderService.getAccountHolders();

        if (retrievedAccountHolders == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            for (AccountHolderDto dto : retrievedAccountHolders) {
                AccountHolderResponseModel responseModel = new AccountHolderResponseModel();
                BeanUtils.copyProperties(dto, responseModel);
                returnValue.add(responseModel);
            }

//            return new ResponseEntity<>(returnValue, HttpStatus.OK);

            return ResponseEntity.ok()
                    .cacheControl(cacheControl)
                    .body(returnValue);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountHolderResponseModel> getAccountHolder(@PathVariable("id") Long id) {

        AccountHolderResponseModel returnValue = new AccountHolderResponseModel();
        AccountHolderDto retrievedAccountHolderDto = accountHolderService.getAccountHolderById(id);

        if (retrievedAccountHolderDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            BeanUtils.copyProperties(retrievedAccountHolderDto, returnValue);
            return new ResponseEntity<>(returnValue, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<AccountHolderResponseModel> createAccountHolder(@Valid @RequestBody AccountHolderRequestModel accountHolderRequest) {

        AccountHolderResponseModel returnValue = new AccountHolderResponseModel();
        AccountHolderDto accountHolderDto = new AccountHolderDto();
        BeanUtils.copyProperties(accountHolderRequest,accountHolderDto);
        AccountHolderDto createdAccountHolder = accountHolderService.createAccountHolder(accountHolderDto);
        BeanUtils.copyProperties(createdAccountHolder, returnValue);

        return new ResponseEntity<>(returnValue, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountHolderResponseModel> updateAccountHolder(@PathVariable("id") Long id, @Valid @RequestBody AccountHolderRequestModel accountHolderRequest) {

        AccountHolderResponseModel returnValue = new AccountHolderResponseModel();
        AccountHolderDto accountHolderDto = new AccountHolderDto();
        BeanUtils.copyProperties(accountHolderRequest, accountHolderDto);
        AccountHolderDto updatedAccountHolder = accountHolderService.updateAccountHolder(id, accountHolderDto);

        if (updatedAccountHolder == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            BeanUtils.copyProperties(updatedAccountHolder, returnValue);
            return new ResponseEntity<>(returnValue, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AccountHolderResponseModel> deleteAccountHolder(@PathVariable("id") Long id) {

        AccountHolderResponseModel returnValue = new AccountHolderResponseModel();
        AccountHolderDto deletedAccountHolder = accountHolderService.deleteAccountHolder(id);

        if (deletedAccountHolder == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            BeanUtils.copyProperties(deletedAccountHolder, returnValue);
            return new ResponseEntity<>(returnValue, HttpStatus.OK);
        }
    }

    @GetMapping("{id}/accounts")
    public ResponseEntity<HashSet<BankAccountResponseModel>> getBankAccountsbyAccountHolderId(@PathVariable("id") Long id) {

        HashSet<BankAccountResponseModel> returnValue = new HashSet<>();
        HashSet<BankAccountDto> retrievedBankAccounts = accountHolderService.getBankAccountsByAccountHolderId(id);

        if (retrievedBankAccounts == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            for (BankAccountDto dto : retrievedBankAccounts) {
                BankAccountResponseModel responseModel = new BankAccountResponseModel();
                BeanUtils.copyProperties(dto, responseModel);
                returnValue.add(responseModel);
            }

            return new ResponseEntity<>(returnValue, HttpStatus.OK);
        }
    }
}
