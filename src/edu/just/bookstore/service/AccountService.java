package edu.just.bookstore.service;

import edu.just.bookstore.dao.AccountDAO;
import edu.just.bookstore.dao.impl.AccountDAOImpl;
import edu.just.bookstore.domain.Account;

public class AccountService {
	
	private AccountDAO accountDAO = new AccountDAOImpl();
	
	public Account getAccount(int accountId) {
		return accountDAO.get(accountId);
	}
	
}
