package com.bm.transfer.service;

import com.bm.transfer.dto.response.TransactionPageResponse;
import com.bm.transfer.dto.request.TransactionRequestDto;

public interface TransactionService {
    void createTransaction(TransactionRequestDto requestDto);

    TransactionPageResponse getTransactionsHistory(int pageNo, int pageSize, String sortBy, String accountNumber);
}
