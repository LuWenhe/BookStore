package edu.just.bookstore.test;

import java.sql.Connection;

import org.junit.jupiter.api.Test;

import edu.just.bookstore.dao.UserDAO;
import edu.just.bookstore.dao.impl.UserDAOImpl;
import edu.just.bookstore.db.JDBCUtils;
import edu.just.bookstore.domain.User;
import edu.just.bookstore.web.ConnectionContext;

class UserDAOTest {
	
	UserDAO userDAO = new UserDAOImpl();

	private Connection connection = JDBCUtils.getConnection();
	
	@Test
	void testGetUser() {
		ConnectionContext.getInstance().bind(connection);
		
		User user = userDAO.getUser("LWH");
		System.out.println(user);
	}

}
