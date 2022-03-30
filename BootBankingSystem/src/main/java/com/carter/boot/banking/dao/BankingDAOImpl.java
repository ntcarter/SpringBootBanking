package com.carter.boot.banking.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.carter.boot.banking.entity.Account;
import com.carter.boot.banking.entity.Transaction;

@Repository
public class BankingDAOImpl implements BankingDAO{

	private EntityManager entityManager;
	
	@Autowired
	public BankingDAOImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}
	
	@Override
	public List<Account> findAll() {
		// get the hibernate session
		Session session = entityManager.unwrap(Session.class);
		
		// the Account in "from Account" refers to the Java class NOT the sql table
		Query<Account> query = session.createQuery("from Account", Account.class); 
		
		// execute query and get the results
		List<Account> accounts = query.getResultList();
		
		return accounts;
	}

	@Override
	public Account findById(int theId) {
		Session session = entityManager.unwrap(Session.class);
		Account accounts = session.get(Account.class, theId);
		return accounts;
	}

	@Override
	public void save(Account user) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(user);
		
	}

	@Override
	public void deleteById(int theId) {
		Session session = entityManager.unwrap(Session.class);
		Query transactionDelete = session.createQuery("delete from Transaction where accountId=:idAccount");
		transactionDelete.setParameter("idAccount", theId);
		
		Query query = session.createQuery("delete from Account where id=:accountId");
		query.setParameter("accountId", theId);

		transactionDelete.executeUpdate();
		query.executeUpdate();
	}

	@Override
	public void accountTransaction(Transaction transaction) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(transaction);
	}

}
