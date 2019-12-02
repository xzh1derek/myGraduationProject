package com.example.dao.daoImpl;

import com.example.dao.IAccountDao;
import com.example.domain.Account;
import com.example.mapper.AccountMapper;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDao implements IAccountDao
{
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Boolean existAccount(String username)
    {
        return accountRepository.existsById(username);
    }

    @Override
    public Account getAccount(String username)
    {
        return accountRepository.getOne(username);
    }
}
