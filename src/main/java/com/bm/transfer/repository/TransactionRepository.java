package com.bm.transfer.repository;

import com.bm.transfer.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {


    @Query("""
            SELECT t
            FROM Transaction t
            WHERE t.user.accountNumber = :accountNumber
            """)
    Page<Transaction> getUserTransactionsHistoryByAccountNumber(String accountNumber, Pageable pageable);
}
