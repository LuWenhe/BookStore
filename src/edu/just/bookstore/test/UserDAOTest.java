package edu.just.bookstore.test;

import org.junit.jupiter.api.Test;

import edu.just.bookstore.dao.UserDAO;
import edu.just.bookstore.dao.impl.UserDAOImpl;
import edu.just.bookstore.domain.User;

class UserDAOTest {
	
	UserDAO userDAO = new UserDAOImpl();

	@Test
	void testGetUser() {
		User user = userDAO.getUser("LWH");
		System.out.println(user);
	}

}
