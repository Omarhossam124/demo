package com.bm.transfer.controller;

import com.bm.transfer.dto.request.AccountUpdateRequest;
import com.bm.transfer.dto.request.TransferRequest;
import com.bm.transfer.dto.response.AccountDetailsResponse;
import com.bm.transfer.service.UserAccountService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@Validated
@CrossOrigin
public class UserAccountController {

    private final UserAccountService service;


    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(
            @Valid @RequestBody TransferRequest request
    ) throws MessagingException {
        return ResponseEntity.ok(service.transfer(request));
    }

    @PutMapping
    public ResponseEntity<String> updateAccount(
            @RequestParam("account-number") String accountNumber,
            @Valid @RequestBody AccountUpdateRequest request
    ) {
        service.updateAccount(accountNumber, request);
        return ResponseEntity.ok("Account with AccountNumber " + accountNumber + " updated successfully.");
    }

    @GetMapping("/current-balance")
    public ResponseEntity<BigDecimal> currentBalance(
            @RequestParam("account-number") String accountNumber
    ) {
        return ResponseEntity.ok(service.currentBalance(accountNumber));
    }

    @GetMapping("/details")
    public ResponseEntity<AccountDetailsResponse> getAccountDetails(
            @RequestParam("account-number") String accountNumber
    ) {
        return ResponseEntity.ok(service.getAccountDetails(accountNumber));
    }
}

