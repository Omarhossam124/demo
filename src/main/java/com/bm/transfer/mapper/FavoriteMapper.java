package com.bm.transfer.mapper;

import com.bm.transfer.dto.request.FavoriteCreateRequest;
import com.bm.transfer.dto.response.FavoriteGetResponse;
import com.bm.transfer.entity.Favorite;
import com.bm.transfer.exceptions.AccountNotFoundException;

import com.bm.transfer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoriteMapper {
    private final UserRepository repository;

    public Favorite mapToFavorite(FavoriteCreateRequest request) {
        return Favorite.builder()
                .recipientName(request.recipientName())
                .user(repository.getUserByAccountNumber(
                        request.accountNumber()
                ).orElseThrow(() -> new AccountNotFoundException(String.format("Account With AccountId:: %s Not Found",request.accountNumber()))))
                .recipientAccountNumber(request.recipientAccountNumber())
                .build();
    }

    public FavoriteGetResponse mapToFavoriteGetResponse(Favorite favorite) {
        return FavoriteGetResponse.builder()
                .recipientName(favorite.getRecipientName())
                .build();
    }
}
