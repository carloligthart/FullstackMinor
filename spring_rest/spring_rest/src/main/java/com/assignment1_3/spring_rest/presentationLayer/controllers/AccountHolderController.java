package com.assignment1_3.spring_rest.presentationLayer.controllers;


import com.assignment1_3.spring_rest.exceptions.AccountHolderNotFoundException;
import com.assignment1_3.spring_rest.exceptions.BankAccountNotFoundException;
import com.assignment1_3.spring_rest.presentationLayer.mappers.AccountHolderMapper;
import com.assignment1_3.spring_rest.presentationLayer.mappers.BankAccountMapper;
import com.assignment1_3.spring_rest.dataLayer.models.AccountHolder;
import com.assignment1_3.spring_rest.dataLayer.models.BankAccount;
import com.assignment1_3.spring_rest.presentationLayer.models.AccountHolderRequestModel;
import com.assignment1_3.spring_rest.presentationLayer.models.AccountHolderResponseModel;
import com.assignment1_3.spring_rest.presentationLayer.models.BankAccountResponseModel;
import com.assignment1_3.spring_rest.businessLayer.AccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/holders")
public class AccountHolderController {

    private final AccountHolderService accountHolderService;
    private final AccountHolderMapper accountHolderMapper;
    private final BankAccountMapper bankAccountMapper;

    @Autowired
    public AccountHolderController(AccountHolderService accountHolderService, AccountHolderMapper accountHolderMapper, BankAccountMapper bankAccountMapper) {
        this.accountHolderService = accountHolderService;
        this.accountHolderMapper = accountHolderMapper;
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

    @GetMapping("")
    public ResponseEntity<Collection<AccountHolderResponseModel>> getAccountHolders() {

        Collection<AccountHolderResponseModel> returnValue = new ArrayList<>();
        Collection<AccountHolder> retrievedAccountHolders = accountHolderService.getAccountHolders();

        if (retrievedAccountHolders == null) {
            throw new AccountHolderNotFoundException("No AccountHolders found");
        } else {
            for (AccountHolder dto : retrievedAccountHolders) {
                AccountHolderResponseModel responseModel = accountHolderMapper.entityObjectToResponseModel(dto);
                returnValue.add(responseModel);
            }

            return ResponseEntity.ok(returnValue);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountHolderResponseModel> getAccountHolder(@PathVariable("id") Long id) {
        Optional<AccountHolder> retrievedAccountHolderDto = accountHolderService.getAccountHolderById(id);
        AccountHolder accountHolder = retrievedAccountHolderDto.orElseThrow( () -> new AccountHolderNotFoundException(id) );
        AccountHolderResponseModel returnValue = accountHolderMapper.entityObjectToResponseModel(accountHolder);
        return ResponseEntity.ok(returnValue);
    }

    @PostMapping
    public ResponseEntity<AccountHolderResponseModel> createAccountHolder(@Valid @RequestBody AccountHolderRequestModel accountHolderRequest) {
        AccountHolder accountHolder = accountHolderMapper.requestToEntityObject(accountHolderRequest);
        AccountHolder createdAccountHolder = accountHolderService.createAccountHolder(accountHolder);
        AccountHolderResponseModel returnValue = accountHolderMapper.entityObjectToResponseModel(createdAccountHolder);

        final URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .path("/{id}")
                .buildAndExpand(returnValue.getId())
                .toUri();

        return ResponseEntity.created(uri).body(returnValue);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountHolderResponseModel> updateAccountHolder(@PathVariable("id") Long id, @Valid @RequestBody AccountHolderRequestModel accountHolderRequest) {
        AccountHolder accountHolder = accountHolderMapper.requestToEntityObject(accountHolderRequest);
        accountHolderService.updateAccountHolder(id, accountHolder);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AccountHolderResponseModel> deleteAccountHolder(@PathVariable("id") Long id) {
        accountHolderService.deleteAccountHolder(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}/accounts")
    public ResponseEntity<Collection<BankAccountResponseModel>> getBankAccountsByAccountHolderId(@PathVariable("id") Long id) {

        Collection<BankAccountResponseModel> returnValue = new ArrayList<>();
        Collection<BankAccount> retrievedBankAccounts = accountHolderService.getBankAccountsByAccountHolderId(id);

        if (retrievedBankAccounts == null) {
            throw new BankAccountNotFoundException("No BankAccounts found");
        }
        else {
            for (BankAccount dto : retrievedBankAccounts) {
                BankAccountResponseModel responseModel = bankAccountMapper.EntityObjectToResponseModel(dto);
                returnValue.add(responseModel);
            }

            return ResponseEntity.ok(returnValue);
        }
    }
}
