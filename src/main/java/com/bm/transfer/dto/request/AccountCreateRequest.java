package com.bm.transfer.dto.request;


import com.bm.transfer.dto.enums.Country;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record AccountCreateRequest(

        @NotNull(message = "FirstName must be required")
        String userName,

        @NotNull(message = "Email must be required")
        String email,

        @NotNull(message = "AccountCurrency must be required")
        Country country,

        @NotNull(message = "DateOfBirth must be required")
        LocalDate dateOfBirth,

        @NotBlank(message = "New password is required")
        @Size(min = 8, message = "New password must be at least 8 characters long")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character"
        )
        String password




) {
}
