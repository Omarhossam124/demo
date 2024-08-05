package com.bm.transfer.dto.request;

import com.bm.transfer.entity.User;
import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

@Builder

public record TransactionRequestDto(

        User userAccount,


        String toAccountNumber,


        BigDecimal amount,

        HttpStatus status
) {
}
