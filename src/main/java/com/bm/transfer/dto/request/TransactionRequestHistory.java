package com.bm.transfer.dto.request;

import org.springframework.data.domain.Sort;

public record TransactionRequestHistory(
        int pageNumb,

        int pageSize,

        Sort sort
) {
}
