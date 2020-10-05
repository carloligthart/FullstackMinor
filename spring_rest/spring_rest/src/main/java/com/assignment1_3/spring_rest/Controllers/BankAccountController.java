package com.assignment1_3.spring_rest.Controllers;

import com.assignment1_3.spring_rest.Exceptions.AccountHolderNotFoundException;
import com.assignment1_3.spring_rest.Exceptions.BankAccountNotFoundException;
import com.assignment1_3.spring_rest.Models.Dto.AccountHolderDto;
import com.assignment1_3.spring_rest.Models.Dto.BankAccountDto;
import com.assignment1_3.spring_rest.Models.Request.BankAccountRequestModel;
import com.assignment1_3.spring_rest.Models.Response.AccountHolderResponseModel;
import com.assignment1_3.spring_rest.Models.Response.BankAccountResponseModel;
import com.assignment1_3.spring_rest.Services.BankAccountService;
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
@RequestMapping("/accounts")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @Autowired
    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
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
        Collection<BankAccountDto> retrievedBankAccounts = bankAccountService.getBankAccounts();

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

    @GetMapping("/{id}")
    public ResponseEntity<BankAccountResponseModel> getBankAccount(@PathVariable("id") Long id) {

        BankAccountResponseModel returnValue = new BankAccountResponseModel();
        BankAccountDto retrievedBankAccountDto = bankAccountService.getBankAccountById(id);

        if (retrievedBankAccountDto == null) {
            throw new BankAccountNotFoundException(id);
        }
        else {
            BeanUtils.copyProperties(retrievedBankAccountDto, returnValue);
            return ResponseEntity.ok(returnValue);
        }
    }

    @PostMapping
    public ResponseEntity<BankAccountResponseModel> createBankAccount(@Valid @RequestBody BankAccountRequestModel bankAccountRequest) {

        BankAccountResponseModel returnValue = new BankAccountResponseModel();
        BankAccountDto bankAccountDto = new BankAccountDto();
        BeanUtils.copyProperties(bankAccountRequest, bankAccountDto);
        BankAccountDto createdBankAccountDto = bankAccountService.createBankAccount(bankAccountDto);
        BeanUtils.copyProperties(createdBankAccountDto, returnValue);

        final URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .path("/{id}")
                .buildAndExpand(returnValue.getId())
                .toUri();

        return ResponseEntity.created(uri).body(returnValue);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BankAccountResponseModel> updateBankAccount(@PathVariable("id") Long id,@Valid @RequestBody BankAccountRequestModel bankAccountRequest) {

        BankAccountResponseModel returnValue = new BankAccountResponseModel();
        BankAccountDto bankAccountDto = new BankAccountDto();
        BeanUtils.copyProperties(bankAccountRequest, bankAccountDto);
        BankAccountDto updatedBankAccountDto = bankAccountService.updateBankAccount(id, bankAccountDto);

        if (updatedBankAccountDto == null) {
            throw new BankAccountNotFoundException(id);
        }
        else {
            BeanUtils.copyProperties(updatedBankAccountDto, returnValue);
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BankAccountResponseModel> deleteBankAccount(@PathVariable("id") Long id) {

        BankAccountResponseModel returnValue = new BankAccountResponseModel();
        BankAccountDto deletedBankAccount = bankAccountService.deleteBankAccount(id);

        if (deletedBankAccount == null) {
            throw new BankAccountNotFoundException(id);
        }
        else {
            BeanUtils.copyProperties(deletedBankAccount, returnValue);
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/{id}/block")
    public ResponseEntity<BankAccountResponseModel> blockBankAccount(@PathVariable("id") Long id) {

        BankAccountResponseModel returnValue = new BankAccountResponseModel();
        BankAccountDto patchedBankAccount = bankAccountService.blockBankAccount(id);

        if (patchedBankAccount == null) {
            throw new BankAccountNotFoundException(id);
        }
        else {
            BeanUtils.copyProperties(patchedBankAccount, returnValue);
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/{id}/unblock")
    public ResponseEntity<BankAccountResponseModel> unBlockBankAccount(@PathVariable("id") Long id) {

        BankAccountResponseModel returnValue = new BankAccountResponseModel();
        BankAccountDto patchedBankAccount = bankAccountService.unBlockBankAccount(id);

        if (patchedBankAccount == null) {
            throw new BankAccountNotFoundException(id);
        }
        else {
            BeanUtils.copyProperties(patchedBankAccount, returnValue);
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("{id}/holders")
    public ResponseEntity<HashSet<AccountHolderResponseModel>> getAccountHoldersByBankAccountId(@PathVariable("id") Long id) {

        HashSet<AccountHolderResponseModel> returnValue = new HashSet<>();
        HashSet<AccountHolderDto> retrievedAccountHolders = bankAccountService.getAccountHoldersByBankAccountId(id);

        if (retrievedAccountHolders == null) {
            throw new AccountHolderNotFoundException("No AccountHolders found");
        }
        else {
            for (AccountHolderDto dto : retrievedAccountHolders) {
                AccountHolderResponseModel responseModel = new AccountHolderResponseModel();
                BeanUtils.copyProperties(dto, responseModel);
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

    @DeleteMapping("{bankAccountId}/holders/{accountHolderId}/unlinkaccount")
    public ResponseEntity<BankAccountResponseModel> unLinkAccountHolder(@PathVariable("bankAccountId") Long bankAccountId, @PathVariable("accountHolderId") Long accountHolderId) {
        bankAccountService.unLinkAccountHolder(bankAccountId, accountHolderId);
        return ResponseEntity.noContent().build();
    }
}
