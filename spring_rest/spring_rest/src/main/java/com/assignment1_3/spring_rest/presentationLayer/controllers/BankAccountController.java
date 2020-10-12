package com.assignment1_3.spring_rest.presentationLayer.controllers;
import com.assignment1_3.spring_rest.Exceptions.AccountHolderNotFoundException;
import com.assignment1_3.spring_rest.Exceptions.BankAccountNotFoundException;
import com.assignment1_3.spring_rest.presentationLayer.mappers.AccountHolderMapper;
import com.assignment1_3.spring_rest.presentationLayer.mappers.BankAccountMapper;
import com.assignment1_3.spring_rest.dataLayer.models.AccountHolder;
import com.assignment1_3.spring_rest.dataLayer.models.BankAccount;
import com.assignment1_3.spring_rest.presentationLayer.models.BankAccountRequestModel;
import com.assignment1_3.spring_rest.presentationLayer.models.AccountHolderResponseModel;
import com.assignment1_3.spring_rest.presentationLayer.models.BankAccountResponseModel;
import com.assignment1_3.spring_rest.businessLayer.BankAccountService;
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
@RequestMapping("/accounts")
public class BankAccountController {

    private final BankAccountService bankAccountService;
    private final AccountHolderMapper accountHolderMapper;
    private final BankAccountMapper bankAccountMapper;

    @Autowired
    public BankAccountController(BankAccountService bankAccountService, AccountHolderMapper accountHolderMapper, BankAccountMapper bankAccountMapper) {
        this.bankAccountService = bankAccountService;
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
    public ResponseEntity<Collection<BankAccountResponseModel>> getBankAccounts() {

        Collection<BankAccountResponseModel> returnValue = new ArrayList<>();
        Collection<BankAccount> retrievedBankAccounts = bankAccountService.getBankAccounts();

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

    @GetMapping("/{id}")
    public ResponseEntity<BankAccountResponseModel> getBankAccount(@PathVariable("id") Long id) {
        Optional<BankAccount> retrievedBankAccountDto = bankAccountService.getBankAccountById(id);
        BankAccount bankAccount = retrievedBankAccountDto.orElseThrow( () -> new BankAccountNotFoundException(id) );
        BankAccountResponseModel returnValue = bankAccountMapper.EntityObjectToResponseModel(bankAccount);
        return ResponseEntity.ok(returnValue);
    }

    @PostMapping
    public ResponseEntity<BankAccountResponseModel> createBankAccount(@Valid @RequestBody BankAccountRequestModel bankAccountRequest) {
        BankAccount bankAccount = bankAccountMapper.requestToEntityObject(bankAccountRequest);
        BankAccount createdBankAccount = bankAccountService.createBankAccount(bankAccount);
        BankAccountResponseModel returnValue = bankAccountMapper.EntityObjectToResponseModel(createdBankAccount);

        final URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .path("/{id}")
                .buildAndExpand(returnValue.getId())
                .toUri();

        return ResponseEntity.created(uri).body(returnValue);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BankAccountResponseModel> updateBankAccount(@PathVariable("id") Long id,@Valid @RequestBody BankAccountRequestModel bankAccountRequest) {
        BankAccount bankAccount = bankAccountMapper.requestToEntityObject(bankAccountRequest);
        bankAccountService.updateBankAccount(id, bankAccount);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BankAccountResponseModel> deleteBankAccount(@PathVariable("id") Long id) {
        bankAccountService.deleteBankAccount(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/block")
    public ResponseEntity<BankAccountResponseModel> blockBankAccount(@PathVariable("id") Long id) {
        bankAccountService.blockBankAccount(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/unblock")
    public ResponseEntity<BankAccountResponseModel> unBlockBankAccount(@PathVariable("id") Long id) {
        bankAccountService.unBlockBankAccount(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}/holders")
    public ResponseEntity<Collection<AccountHolderResponseModel>> getAccountHoldersByBankAccountId(@PathVariable("id") Long id) {

        Collection<AccountHolderResponseModel> returnValue = new ArrayList<>();
        Collection<AccountHolder> retrievedAccountHolders = bankAccountService.getAccountHoldersByBankAccountId(id);

        if (retrievedAccountHolders == null) {
            throw new AccountHolderNotFoundException("No AccountHolders found");
        }
        else {
            for (AccountHolder dto : retrievedAccountHolders) {
                AccountHolderResponseModel responseModel = accountHolderMapper.entityObjectToResponseModel(dto);
                returnValue.add(responseModel);
            }

            return ResponseEntity.ok(returnValue);
        }
    }

    @PutMapping("{bankAccountId}/holders/{accountHolderId}/linkaccount")
    public ResponseEntity<BankAccountResponseModel> linkAccountHolder(@PathVariable("bankAccountId") Long bankAccountId, @PathVariable("accountHolderId") Long accountHolderId) {
        bankAccountService.linkAccountHolder(bankAccountId, accountHolderId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{bankAccountId}/holders/{accountHolderId}/unlinkaccount")
    public ResponseEntity<BankAccountResponseModel> unLinkAccountHolder(@PathVariable("bankAccountId") Long bankAccountId, @PathVariable("accountHolderId") Long accountHolderId) {
        bankAccountService.unLinkAccountHolder(bankAccountId, accountHolderId);
        return ResponseEntity.noContent().build();
    }
}
