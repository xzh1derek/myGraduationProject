package com.example.service.serviceImpl;


import com.example.dao.IAccountDao;
import com.example.domain.Account;
import com.example.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements IAccountService
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

    @Override
    public void updatePassword(String userId, String password)
    {
        accountDao.updatePassword(userId,password);
    }

    @Override
    public void createAccount(Account account)
    {
        accountDao.createAccount(account);
    }

    @Override
    public void deleteAccount(String username)
    {
        accountDao.deleteAccount(username);
    }
}
