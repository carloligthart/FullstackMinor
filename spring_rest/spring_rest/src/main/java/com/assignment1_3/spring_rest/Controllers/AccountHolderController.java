package com.assignment1_3.spring_rest.Controllers;

import com.assignment1_3.spring_rest.Exceptions.AccountHolderNotFoundException;
import com.assignment1_3.spring_rest.Exceptions.BankAccountNotFoundException;
import com.assignment1_3.spring_rest.Models.Dto.AccountHolderDto;
import com.assignment1_3.spring_rest.Models.Dto.BankAccountDto;
import com.assignment1_3.spring_rest.Models.Request.AccountHolderRequestModel;
import com.assignment1_3.spring_rest.Models.Response.AccountHolderResponseModel;
import com.assignment1_3.spring_rest.Models.Response.BankAccountResponseModel;
import com.assignment1_3.spring_rest.Services.AccountHolderService;
import org.springframework.beans.BeanUtils;
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
    public ResponseEntity<Collection<AccountHolderResponseModel>> getAccountHolders() {

        Collection<AccountHolderResponseModel> returnValue = new ArrayList<>();
        Collection<AccountHolderDto> retrievedAccountHolders = accountHolderService.getAccountHolders();

        if (retrievedAccountHolders == null) {
            throw new AccountHolderNotFoundException("No AccountHolders found");
        } else {
            for (AccountHolderDto dto : retrievedAccountHolders) {
                AccountHolderResponseModel responseModel = new AccountHolderResponseModel();
                BeanUtils.copyProperties(dto, responseModel);
                returnValue.add(responseModel);
            }

            return ResponseEntity.ok(returnValue);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountHolderResponseModel> getAccountHolder(@PathVariable("id") Long id) {

        AccountHolderResponseModel returnValue = new AccountHolderResponseModel();
        AccountHolderDto retrievedAccountHolderDto = accountHolderService.getAccountHolderById(id);

        if (retrievedAccountHolderDto == null) {
            throw new AccountHolderNotFoundException(id);
        }
        else {
            BeanUtils.copyProperties(retrievedAccountHolderDto, returnValue);
            return ResponseEntity.ok(returnValue);
        }
    }

    @PostMapping
    public ResponseEntity<AccountHolderResponseModel> createAccountHolder(@Valid @RequestBody AccountHolderRequestModel accountHolderRequest) {

        AccountHolderResponseModel returnValue = new AccountHolderResponseModel();
        AccountHolderDto accountHolderDto = new AccountHolderDto();
        BeanUtils.copyProperties(accountHolderRequest,accountHolderDto);
        AccountHolderDto createdAccountHolder = accountHolderService.createAccountHolder(accountHolderDto);
        BeanUtils.copyProperties(createdAccountHolder, returnValue);

        final URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .path("/{id}")
                .buildAndExpand(returnValue.getId())
                .toUri();

        return ResponseEntity.created(uri).body(returnValue);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountHolderResponseModel> updateAccountHolder(@PathVariable("id") Long id, @Valid @RequestBody AccountHolderRequestModel accountHolderRequest) {

        AccountHolderResponseModel returnValue = new AccountHolderResponseModel();
        AccountHolderDto accountHolderDto = new AccountHolderDto();
        BeanUtils.copyProperties(accountHolderRequest, accountHolderDto);
        AccountHolderDto updatedAccountHolder = accountHolderService.updateAccountHolder(id, accountHolderDto);

        if (updatedAccountHolder == null) {
            throw new AccountHolderNotFoundException(id);
        }
        else {
            BeanUtils.copyProperties(updatedAccountHolder, returnValue);
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AccountHolderResponseModel> deleteAccountHolder(@PathVariable("id") Long id) {

        AccountHolderResponseModel returnValue = new AccountHolderResponseModel();
        AccountHolderDto deletedAccountHolder = accountHolderService.deleteAccountHolder(id);

        if (deletedAccountHolder == null) {
            throw new AccountHolderNotFoundException(id);
        } else {
            BeanUtils.copyProperties(deletedAccountHolder, returnValue);
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("{id}/accounts")
    public ResponseEntity<HashSet<BankAccountResponseModel>> getBankAccountsByAccountHolderId(@PathVariable("id") Long id) {

        HashSet<BankAccountResponseModel> returnValue = new HashSet<>();
        HashSet<BankAccountDto> retrievedBankAccounts = accountHolderService.getBankAccountsByAccountHolderId(id);

        if (retrievedBankAccounts == null) {
            throw new BankAccountNotFoundException("No BankAccounts found");
        }
        else {
            for (BankAccountDto dto : retrievedBankAccounts) {
                BankAccountResponseModel responseModel = new BankAccountResponseModel();
                BeanUtils.copyProperties(dto, responseModel);
                returnValue.add(responseModel);
            }

            return ResponseEntity.ok(returnValue);
        }
    }
}
