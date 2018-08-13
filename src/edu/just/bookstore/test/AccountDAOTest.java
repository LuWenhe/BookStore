package edu.just.bookstore.test;

import java.sql.Connection;
import java.sql.Date;

import org.junit.jupiter.api.Test;

import edu.just.bookstore.dao.AccountDAO;
import edu.just.bookstore.dao.impl.AccountDAOImpl;
import edu.just.bookstore.db.JDBCUtils;
import edu.just.bookstore.domain.Account;
import edu.just.bookstore.web.ConnectionContext;

class AccountDAOTest {

	AccountDAO accountDAO = new AccountDAOImpl();
	
	private Connection connection = JDBCUtils.getConnection();

	@Test
	void testGet() {
		ConnectionContext.getInstance().bind(connection);
		
		Account account = accountDAO.get(1);
		System.out.println(account);
	}

	@Test
	void testUpdateBalance() {
		accountDAO.updateBalance(1, 100);
	}

	@Test
	void test() {
		System.out.println(new Date(new java.util.Date().getTime()));
	}
}
