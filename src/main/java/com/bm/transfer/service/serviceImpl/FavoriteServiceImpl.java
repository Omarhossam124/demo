package com.bm.transfer.service.serviceImpl;

import com.bm.transfer.dto.request.FavoriteCreateRequest;
import com.bm.transfer.dto.response.FavoriteGetResponse;
import com.bm.transfer.exceptions.FavoriteNotFoundException;
import com.bm.transfer.mapper.FavoriteMapper;
import com.bm.transfer.service.FavoriteService;
import com.bm.transfer.repository.FavoriteRepository;
import com.bm.transfer.exceptions.AccountNotFoundException;

import com.bm.transfer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository repository;
    private final FavoriteMapper mapper;
    private final UserRepository accountRepository;



    @Override
    public void AddFavoriteRecipient(FavoriteCreateRequest request) {

        // check account
        var account =  accountRepository.getUserByAccountNumber(request.accountNumber())
                        .orElseThrow( () -> new AccountNotFoundException(String.format("Account Not Found With ID:: %s", request.accountNumber())));
        // check favorite account
         accountRepository.getUserByAccountNumberAndUserName(request.recipientAccountNumber(), request.recipientName())
                .orElseThrow( () -> new AccountNotFoundException(String.format("Account Not Found With AccountNumber:: %s And RecipientName:: %s", request.recipientAccountNumber(), request.recipientName())));


         repository.save(mapper.mapToFavorite(request));
    }
    @Cacheable(value = "Favorite.getFavoriteRecipients", key = "#raccountId")
    @Override
    public List<FavoriteGetResponse> getFavoriteRecipients(String accountNumber) {

        accountRepository.getUserByAccountNumber(accountNumber).orElseThrow(
                () -> new AccountNotFoundException(String.format("Account Not Found With ID:: %s", accountNumber))
        );

        return repository.getFavoriteRecipients(accountNumber)
                .stream()
                .map(mapper::mapToFavoriteGetResponse)
                .toList();
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteFavoriteRecipient(String accountNumber, String recipientAccountNumber) {
        accountRepository.getUserByAccountNumber(accountNumber).orElseThrow(
                () -> new AccountNotFoundException(String.format("Account Not Found With AccountNumber:: %s", accountNumber))
        );

        accountRepository.getUserByAccountNumber(recipientAccountNumber)
                .orElseThrow(
                        () -> new AccountNotFoundException(String.format("RecipientAccountNumber not found with recipientAccountNumber:: %s", recipientAccountNumber))
                );
        int a = repository.deleteFavoritesByUserAccountNumberAndRecipientAccountNumber(accountNumber, recipientAccountNumber);

        if (a == 0){
            throw new FavoriteNotFoundException(
                    String.format("Not Found Favorite Recipient %s", recipientAccountNumber)
            );
        }
    }
}
