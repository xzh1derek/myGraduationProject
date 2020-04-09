package com.example.dao.daoImpl;

import com.example.dao.IAccountDao;
import com.example.domain.Account;
import com.example.mapper.AccountMapper;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDaoImpl implements IAccountDao
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

    @Override
    public void updatePassword(String u,String p)
    {
        accountMapper.updatePassword(u,p);
    }

    @Override
    public void createAccount(Account account)
    {
        accountMapper.createAccount(account);
    }

    @Override
    public void deleteAccount(String username)
    {
        accountMapper.deleteAccount(username);
    }

    @Override
    public void updateIdentity(String username, Integer identity)
    {
        accountMapper.updateIdentity(username,identity);
    }
}
