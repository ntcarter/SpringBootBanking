package com.carter.boot.banking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.carter.boot.banking.dao.BankingDAO;
import com.carter.boot.banking.entity.Account;
import com.carter.boot.banking.entity.Transaction;

@Service
public class AccountServiceImpl implements AccountService {
	
	private BankingDAO bankingDAO;
	
	@Autowired
	public AccountServiceImpl(BankingDAO theBankingDAO) {
		bankingDAO = theBankingDAO;
	}

	@Override
	@Transactional
	public List<Account> findAll() {
		return bankingDAO.findAll();
	}

	@Override
	@Transactional
	public Account findById(int theId) {
		return bankingDAO.findById(theId);
	}

	@Override
	@Transactional
	public void save(Account account) {
		bankingDAO.save(account);
	}

	@Override
	@Transactional
	public void deleteById(int theId) {
		bankingDAO.deleteById(theId);
	}

	@Override
	public void saveTransaction(Transaction transaction) {
		bankingDAO.accountTransaction(transaction);
	}
	
	
}
