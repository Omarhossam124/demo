package com.bm.transfer.repository;

import com.bm.transfer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    @Query(value = """
     SELECT u.balance
     FROM User u
     WHERE u.accountNumber like :accountNumber
     """)
    Optional<BigDecimal> getCurrentBalance(String accountNumber);


    Optional<User> getUserByAccountNumberAndUserName(String accountNumber, String UserName);

    Optional<User>getUserByAccountNumber(String accountNumber);
}
