package com.bm.transfer.mapper;

import com.bm.transfer.dto.response.AccountDetailsResponse;
import com.bm.transfer.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public AccountDetailsResponse mapToAccountDetailsResponse(User user) {

        return new AccountDetailsResponse(
                user.getAccountNumber(),
                user.getUsername(),
                user.getEmail(),
                user.getCountry().toString(),
                user.getBranch().toString(),
                user.getDateOfBirth().toString()
        );
    }
}
