package com.bm.transfer.dto.response;

import com.bm.transfer.dto.enums.Country;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;



@Builder
public record AccountResponseDto(
         Long id,

         String accountNumber,

         String userName,

         String email,

         LocalDate dateOfBirth,

         Country country,

         BigDecimal balance,

         LocalDateTime createdAt
) {
}
