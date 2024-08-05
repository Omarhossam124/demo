package com.bm.transfer.dto.response

import com.bm.transfer.dto.enums.Branch
import java.math.BigDecimal


data class AccountDetailsResponse(
    val accountNumber: String,
    val userName: String,
    val email: String,
    val country: String,
    val branch: String,
    val dateOfBirth: String,

    )