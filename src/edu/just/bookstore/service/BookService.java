package edu.just.bookstore.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import edu.just.bookstore.dao.AccountDAO;
import edu.just.bookstore.dao.BookDAO;
import edu.just.bookstore.dao.TradeDAO;
import edu.just.bookstore.dao.TradeItemDAO;
import edu.just.bookstore.dao.UserDAO;
import edu.just.bookstore.dao.impl.AccountDAOImpl;
import edu.just.bookstore.dao.impl.BookDAOImpl;
import edu.just.bookstore.dao.impl.TradeDAOImpl;
import edu.just.bookstore.dao.impl.TradeItemDAOImpl;
import edu.just.bookstore.dao.impl.UserDAOImpl;
import edu.just.bookstore.domain.Book;
import edu.just.bookstore.domain.ShoppingCart;
import edu.just.bookstore.domain.ShoppingCartItem;
import edu.just.bookstore.domain.Trade;
import edu.just.bookstore.domain.TradeItem;
import edu.just.bookstore.web.CriteriaBook;
import edu.just.bookstore.web.Page;

public class BookService {
	
	private BookDAO bookDAO = new BookDAOImpl();
	
	private AccountDAO accountDAO = new AccountDAOImpl();

	private TradeDAO tradeDAO = new TradeDAOImpl();
	
	private TradeItemDAO tradeItemDAO = new TradeItemDAOImpl();
	
	private UserDAO userDAO = new UserDAOImpl();
	
	public Page<Book> getPage(CriteriaBook criteriaBook){
		Page<Book> page = bookDAO.getPage(criteriaBook);
		
		return page;
	}
	
	public Book getBook(int id) {
		return bookDAO.getBook(id);
	}
	
	//�ӹ��ﳵ���Ƴ���Ʒ
	public void removeItemFromShoppingCart(int id, ShoppingCart sc) {
		sc.removeItem(id);
	}
	
	//��չ��ﳵ
	public void clear(ShoppingCart sc) {
		sc.clear();
	}

	//�����Ʒ�����ﳵ��
	public boolean addTocart(int id, ShoppingCart sc) {
		Book book = bookDAO.getBook(id);
		
		if(book != null) {
			sc.addBook(book);
			return true;
		} 
		
		return false;
	}

	public void updateItemQuantity(ShoppingCart sc, int id, int quantity) {
		sc.updateItemQuantity(id, quantity);
	}
	
	//ҵ�񷽷�
	public void cash(ShoppingCart shoppingCart, String username, String accountId) {
		//1.���� mybooks ���ݱ���ؼ�¼�� salesamount, storeanumber
		bookDAO.batchUpdateStoreNumberAndSalesAmount(shoppingCart.getItems());
		
		//2.���� account ���ݱ�� balance
		accountDAO.updateBalance(Integer.parseInt(accountId), shoppingCart.getTotalMoney());
		
		//3.�� trade ���ݱ����һ����¼
		Trade trade = new Trade();
		trade.setUserId(userDAO.getUser(username).getUserId());
		trade.setTradeTime(new Date(new java.util.Date().getTime()));
		tradeDAO.insert(trade);
		
		//4. �� tradeItem ���ݱ���� n ����¼
		/**
		 * TradeItem <-- ShoppingCartIem <-- Book
		 * 			 <-- Trade
		 */
		Collection<TradeItem> items = new ArrayList<>();
		for(ShoppingCartItem sc: shoppingCart.getItems()) {
			TradeItem tradeItem = new TradeItem();
			
			tradeItem.setBookId(sc.getBook().getId());
			tradeItem.setQuantity(sc.getQuantity());
			tradeItem.setTradeId(trade.getTradeId());

			items.add(tradeItem);
		}
		
		tradeItemDAO.batchSave(items);
		
		//5. ��չ��ﳵ
		shoppingCart.clear(); 
	}
}
