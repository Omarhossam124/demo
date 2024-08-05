package com.bm.transfer.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record FavoriteCreateRequest(

        @NotNull(message = "Account ID cannot be null")
        String accountNumber,

        @NotBlank(message = "Recipient name cannot be blank")
        @Size(max = 100, message = "Recipient name must be less than 100 characters")
        String recipientName,

        @NotBlank(message = "Recipient account number cannot be blank")
        @Pattern(regexp = "\\d{3}-\\d{6}", message = "Recipient account number must be in the format XXX-XXXXXX, where X is a digit")
        String recipientAccountNumber
) {
}
