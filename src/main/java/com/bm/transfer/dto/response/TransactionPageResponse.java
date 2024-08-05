package com.bm.transfer.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record TransactionPageResponse(

        List<TransactionResponseDto> transactionsHistoryForThisAccount,

        Integer pageNumber,

        Integer pageSize,

        int totalElement,

        int totalPages,

        boolean isLast
) {
}
