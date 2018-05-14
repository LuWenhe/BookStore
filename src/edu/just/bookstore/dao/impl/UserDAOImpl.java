package edu.just.bookstore.dao.impl;

import edu.just.bookstore.dao.UserDAO;
import edu.just.bookstore.domain.User;

public class UserDAOImpl extends BaseDAO<User> implements UserDAO {

	@Override
	public User getUser(String username) {
		String sql = "SELECT userid, username, accountid FROM userinfo "
				+ "WHERE username = ?";
		return query(sql, username);
	}

}
