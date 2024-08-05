package com.bm.transfer.service.serviceImpl;


import com.bm.transfer.dto.request.AccountUpdateRequest;
import com.bm.transfer.dto.request.TransferRequest;
import com.bm.transfer.dto.response.AccountDetailsResponse;
import com.bm.transfer.exceptions.AccountNotFoundException;
import com.bm.transfer.exceptions.InsufficientBalanceException;
import com.bm.transfer.exceptions.PasswordException;

import com.bm.transfer.entity.User;
import com.bm.transfer.mapper.UserMapper;
import com.bm.transfer.repository.UserRepository;
import com.bm.transfer.email.SendEmailService;
import com.bm.transfer.dto.request.TransactionRequestDto;
import com.bm.transfer.service.TransactionService;

import com.bm.transfer.service.UserAccountService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserAccountServicedImpl implements UserAccountService {


    private final SendEmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final TransactionService transactionService;
    private final UserRepository repository;
    private final UserMapper userMapper;


    @NotNull
    @Transactional(
            propagation = Propagation.REQUIRED,
            isolation = Isolation.SERIALIZABLE
    )

    @RateLimiter(name = "accountTransferBreaker")
    @Override
    public String transfer(TransferRequest request) throws MessagingException {

        Long fromId = request.fromId();
        String toAccountNumber = request.toAccountNumber();
        BigDecimal amount = request.amount();

        User accountFrom = getAccount(fromId);
        User accountTo = getRecipientAccount(toAccountNumber);


        validateBalance(accountFrom.getBalance(), amount);

        performTransfer(accountFrom, accountTo, amount);

        createTransaction( toAccountNumber, accountFrom, amount);

        sendEmails(accountFrom, accountTo, amount);

        return String.format("Transferred %s from account ID %s to account ID %s", amount, fromId, toAccountNumber);
    }

    private User getAccount(Long accountId) {
        return repository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(String.format("Account not found with ID: %s", accountId)));
    }

    private User getRecipientAccount(String accountNumber) {
        return repository.getUserByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(String.format("Recipient account not found with number: %s", accountNumber)));
    }

    private void validateBalance(BigDecimal balance, BigDecimal amount) {
        if (!isAmountValid(balance, amount)) {
            throw new InsufficientBalanceException("Insufficient balance for the transaction.");
        }
    }

    private boolean isAmountValid(BigDecimal balance, BigDecimal amount) {
        return balance.subtract(amount).compareTo(BigDecimal.ZERO) >= 0;
    }

    private void performTransfer(User accountFrom, User accountTo, BigDecimal amount) {
        BigDecimal transferThreshold = new BigDecimal(5000);

        if (amount.subtract(transferThreshold).compareTo(BigDecimal.ZERO) >= 0) {
            accountFrom.setBalance(accountFrom.getBalance().subtract(amount));
            accountTo.setBalance(accountTo.getBalance().add(amount));

            repository.save(accountFrom);
            repository.save(accountTo);
        }
    }

    private void createTransaction( String toAccountNumber, User userAccountFrom, BigDecimal amount) {

        HttpStatus status = amount.compareTo(BigDecimal.valueOf(500)) < 0 ? HttpStatus.CONFLICT : HttpStatus.CREATED;
        var transaction = TransactionRequestDto.builder()
                .userAccount(userAccountFrom)
                .toAccountNumber(toAccountNumber)
                .amount(amount)
                .status(status) // Status logic can be improved if needed
                .build();

        transactionService.createTransaction(transaction);
    }

    private void sendEmails(User accountFrom, User accountTo, BigDecimal amount) throws MessagingException {
        Map<String, Object> templateModelFrom = new HashMap<>();
        templateModelFrom.put("name", accountFrom.getUsername());
        templateModelFrom.put("amount", amount);
        emailService.sendEmail(accountFrom.getEmail(), templateModelFrom, "email-template.html");

        Map<String, Object> templateModelTo = new HashMap<>();
        templateModelTo.put("name", accountTo.getUsername());
        templateModelTo.put("amount", amount);
        templateModelTo.put("sender", accountFrom.getUsername());
        emailService.sendEmail(accountTo.getEmail(), templateModelTo, "recipient-email-template.html");
    }








    @NotNull
    @Cacheable(value = "Account.currentBalance", key = "#accountNumber")
    @Override
    public BigDecimal currentBalance(@NotNull String accountNumber) {

        return repository.getCurrentBalance(accountNumber)
                .orElseThrow(() -> new InsufficientBalanceException("InsufficientBalanceException"));
    }



    @Override
    public void updateAccount(
            @NotNull String accountNumber,
            AccountUpdateRequest request
    ) {

        var account = repository.getUserByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(String.format("Account Not found with AccountNumber:: %s", accountNumber)));

        if (!isCurrentPasswordValid(account, request.currentPassword())) {
            throw new PasswordException("You must enter the correct current password.");
        }

         // Hash the new password before saving
        String hashedPassword = passwordEncoder.encode(request.newPassword());
        account.setPassword(hashedPassword);
        repository.save(account);
    }



    private boolean isCurrentPasswordValid(User account, String currentPassword){
        return passwordEncoder.matches(currentPassword, account.getPassword());
    }


    private String generateSequenceNumber() {

        return String.format("%06d", UUID.randomUUID().toString().hashCode() & 0x7FFFFFFF).substring(0, 6);
    }
    private String generateSequenceNumberFromAccountCurrency(String accountCurrency){

         var ch1 = accountCurrency.charAt(0)-'A';
         var ch2 = accountCurrency.charAt(1) - 'A';
         var ch3 = accountCurrency.charAt(2) - 'A';
         return ch1+""+ch2+""+ch3+"";
    }

    public String generateAccountNumber(String accountCurrency){
        return generateSequenceNumberFromAccountCurrency(accountCurrency).substring(0,3) + "-".concat(generateSequenceNumber());
    }


    @NotNull
    @Override
    public AccountDetailsResponse getAccountDetails(@NotNull String accountNumber){
        return repository.getUserByAccountNumber(accountNumber)
                .map(userMapper::mapToAccountDetailsResponse)
                .orElseThrow(() -> new AccountNotFoundException(String.format("Account not found with accountNumber:: %s", accountNumber)));

    }



}
