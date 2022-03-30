package com.carter.boot.banking.service;

import java.util.List;

import com.carter.boot.banking.entity.Account;
import com.carter.boot.banking.entity.Transaction;

public interface AccountService {
	public List<Account> findAll();
	
	public Account findById(int theId);
	
	public void save(Account account);
	
	public void deleteById(int theId);
	
	public void saveTransaction(Transaction transaction);
}
