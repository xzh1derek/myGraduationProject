package com.example.service.serviceImpl;


import com.example.dao.IAccountDao;
import com.example.domain.Account;
import com.example.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements IAccountService
{
    @Autowired
    private IAccountDao accountDao;

    @Override
    public boolean existAccount(String username)
    {
        return accountDao.existAccount(username);
    }

    @Override
    public Account getAccount(String username)
    {
        return accountDao.getAccount(username);
    }
}
