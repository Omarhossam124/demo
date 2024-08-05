package com.bm.transfer.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransferRequest(
        @NotNull(message = "fromId is required")
        @Min(value = 1, message = "fromId must be greater than 0")
         Long fromId,

        @NotNull(message = "toId is required")
        String  toAccountNumber,

        @NotNull(message = "amount is required")
        @Min(value = 0, message = "amount must be a positive value")
        BigDecimal amount

) {
}
