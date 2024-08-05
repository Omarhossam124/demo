package com.bm.transfer.service

import com.bm.transfer.dto.request.AccountUpdateRequest
import com.bm.transfer.dto.request.TransferRequest
import com.bm.transfer.dto.response.AccountDetailsResponse
import jakarta.mail.MessagingException
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
interface UserAccountService {

    fun getAccountDetails(accountNumber: String): AccountDetailsResponse

    @Throws(MessagingException::class)
    fun transfer(request: TransferRequest): String

    fun currentBalance(accountNumber: String): BigDecimal

    fun updateAccount(accountNumber: String, request: AccountUpdateRequest)
}