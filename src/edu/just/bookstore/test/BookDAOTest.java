package edu.just.bookstore.test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Test;

import edu.just.bookstore.dao.BookDAO;
import edu.just.bookstore.dao.impl.BookDAOImpl;
import edu.just.bookstore.db.JDBCUtils;
import edu.just.bookstore.domain.Book;
import edu.just.bookstore.domain.ShoppingCartItem;
import edu.just.bookstore.web.ConnectionContext;
import edu.just.bookstore.web.CriteriaBook;
import edu.just.bookstore.web.Page;

class BookDAOTest {
	
	BookDAO bookDAO = new BookDAOImpl();

	private Connection connection = JDBCUtils.getConnection();
	
	@Test
	void testGetBook() {
		ConnectionContext.getInstance().bind(connection);
		
		Book book = bookDAO.getBook(5);
		System.out.println(book);
	}

	@Test
	void testGetPage() {
		ConnectionContext.getInstance().bind(connection);
		
		CriteriaBook cb = new CriteriaBook(50, 60, 1);
		Page<Book> page = bookDAO.getPage(cb);
		
		System.out.println("PageNo: " + page.getPageNo());
		System.out.println("PageSize: " + page.getPageSize());
		System.out.println("List: " + page.getList());
		System.out.println("TotalPageNumber: " + page.getTotalPageNumber());
		System.out.println("PrevPage: " + page.getPrevPage());
		System.out.println("NextPage: " + page.getNextPage());
	}

	@Test
	void testGetStoreNumber() {
		ConnectionContext.getInstance().bind(connection);
		
		int number = bookDAO.getStoreNumber(5);
		System.out.println(number);
	}

	@Test
	void testBatchUpdateStoreNumberAndSalesAmount() {
		ConnectionContext.getInstance().bind(connection);

		Collection<ShoppingCartItem> items = new ArrayList<>();
		
		Book book = bookDAO.getBook(1);
		ShoppingCartItem sc = new ShoppingCartItem(book);
		sc.setQuantity(10);
		items.add(sc);
		
		book = bookDAO.getBook(2);
		sc = new ShoppingCartItem(book);
		sc.setQuantity(10);
		items.add(sc);
		
		book = bookDAO.getBook(3);
		sc = new ShoppingCartItem(book);
		sc.setQuantity(10);
		items.add(sc);
		
		book = bookDAO.getBook(4);
	    sc = new ShoppingCartItem(book);
		sc.setQuantity(10);
		items.add(sc);
		
		bookDAO.batchUpdateStoreNumberAndSalesAmount(items);
	}
}
