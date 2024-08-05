package com.bm.transfer.controller

import com.bm.transfer.dto.response.TransactionPageResponse
import com.bm.transfer.service.TransactionService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/transactions")
@CrossOrigin
open class TransactionController(
    private val service: TransactionService
) {

    @GetMapping("/transaction")
    fun getTransactionsHistory(
        @RequestParam(defaultValue = "0") pageNo: Int,
        @RequestParam(defaultValue = "5") pageSize: Int,
        @RequestParam(defaultValue = "id") sortBy: String,
        @RequestParam("account-number") accountNumber: String
    ): TransactionPageResponse {
        return service.getTransactionsHistory(pageNo, pageSize, sortBy, accountNumber)
    }
}
