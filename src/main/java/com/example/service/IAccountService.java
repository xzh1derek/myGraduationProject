package com.example.service;

import com.example.domain.Account;

public interface IAccountService
{
    boolean existAccount(String username);

    Account getAccount(String username);

    void updatePassword(String userId, String password);

    void createAccount(Account account);

    void deleteAccount(String username);

}
