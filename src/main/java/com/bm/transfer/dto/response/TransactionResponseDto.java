package com.bm.transfer.dto.response;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record TransactionResponseDto(

        String recipient,

        BigDecimal amount,

        LocalDateTime transactionDate,

        HttpStatus status
) {
}
