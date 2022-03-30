package com.carter.boot.banking.dao;

import java.util.List;

import com.carter.boot.banking.entity.Account;
import com.carter.boot.banking.entity.Transaction;

public interface BankingDAO {

	public List<Account> findAll();
	
	public Account findById(int theId);
	
	public void save(Account user);
	
	public void deleteById(int theId);
	
	public void accountTransaction(Transaction transaction);
}
