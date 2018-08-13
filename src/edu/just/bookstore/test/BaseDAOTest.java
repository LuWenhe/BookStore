package edu.just.bookstore.test;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import edu.just.bookstore.dao.impl.BookDAOImpl;
import edu.just.bookstore.db.JDBCUtils;
import edu.just.bookstore.domain.Book;
import edu.just.bookstore.web.ConnectionContext;

class BaseDAOTest {

	private BookDAOImpl bookDAOImpl = new BookDAOImpl();
	
	private Connection connection = JDBCUtils.getConnection();
	
	@Test
	void testInsert() {
		ConnectionContext.getInstance().bind(connection);
		String sql = "INSERT INTO trade(userid, tradetime) VALUES(?, ?) ";
		long id = bookDAOImpl.insert(sql, 1, new Date(new java.util.Date().getTime()));
		
		System.out.println(id);
	}

	@Test
	void testUpdate() {
		ConnectionContext.getInstance().bind(connection);
		String sql = "UPDATE mybooks SET salesamount = ? WHERE id = ?";
		bookDAOImpl.update(sql, 10, 4);
	}

	@Test
	void testQuery() {
		ConnectionContext.getInstance().bind(connection);
		String sql = "SELECT id, author, title, price, publishingdate, salesamount,"
				+ " storenumber, remark FROM mybooks WHERE id = ?";
		
		Book book = bookDAOImpl.query(sql, 4);
		System.out.println(book);
	}

	@Test
	void testQueryForList() {
		ConnectionContext.getInstance().bind(connection);
		String sql = "SELECT id, author, title, price, publishingdate, salesamount,"
				+ " storenumber, remark FROM mybooks WHERE id < ?";
		
		List<Book> books = bookDAOImpl.queryForList(sql, 5);
		System.out.println(books);
	}

	@Test
	void testGetSingleVal() {
		ConnectionContext.getInstance().bind(connection);
		String sql = "SELECT count(id) FROM mybooks";
		long count = bookDAOImpl.getSingleVal(sql);
		
		System.out.println(count);
	}

	@Test
	void testBatch() {
		ConnectionContext.getInstance().bind(connection);
		String sql = "UPDATE mybooks SET salesamount = ?, storenumber = ? " + 
					"WHere id = ?";
		
		bookDAOImpl.batch(sql, new Object[] {1,1,1}, new Object[] {1,1,2}, new Object[] {1,1,3});
	}

}
