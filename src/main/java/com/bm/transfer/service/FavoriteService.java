package com.bm.transfer.service;

import com.bm.transfer.dto.request.FavoriteCreateRequest;
import com.bm.transfer.dto.response.FavoriteGetResponse;

import java.util.List;

public interface FavoriteService {


    void AddFavoriteRecipient(FavoriteCreateRequest request);

    List<FavoriteGetResponse> getFavoriteRecipients(String accountNumber);

    void deleteFavoriteRecipient(String accountNumber, String recipientAccountNumber);
}
