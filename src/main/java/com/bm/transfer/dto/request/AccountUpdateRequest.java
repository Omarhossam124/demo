package com.bm.transfer.dto.request;

import jakarta.validation.constraints.*;

public record AccountUpdateRequest(

        @NotBlank(message = "Current password is required")
        String currentPassword,

        @NotBlank(message = "New password is required")
        @Size(min = 8, message = "New password must be at least 8 characters long")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "New password must contain at least one uppercase letter, one lowercase letter, one number, and one special character"
        )
        String newPassword
) {
}