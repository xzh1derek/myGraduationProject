package com.example.dao;

import com.example.domain.Account;

public interface IAccountDao
{
    Boolean existAccount(String username);

    Account getAccount(String username);

    void updatePassword(String username, String password);

    void createAccount(Account account);

    void deleteAccount(String username);

    void updateIdentity(String username,Integer identity);
}
