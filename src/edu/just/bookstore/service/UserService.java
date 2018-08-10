package edu.just.bookstore.service;

import java.util.Set;

import edu.just.bookstore.dao.BookDAO;
import edu.just.bookstore.dao.TradeDAO;
import edu.just.bookstore.dao.TradeItemDAO;
import edu.just.bookstore.dao.UserDAO;
import edu.just.bookstore.dao.impl.BookDAOImpl;
import edu.just.bookstore.dao.impl.TradeDAOImpl;
import edu.just.bookstore.dao.impl.TradeItemDAOImpl;
import edu.just.bookstore.dao.impl.UserDAOImpl;
import edu.just.bookstore.domain.Trade;
import edu.just.bookstore.domain.TradeItem;
import edu.just.bookstore.domain.User;

public class UserService {
	
	private UserDAO userDAO = new UserDAOImpl();
	
	private TradeDAO tradeDAO = new TradeDAOImpl();
	
	private TradeItemDAO tradeItemDAO = new TradeItemDAOImpl();
	
	private BookDAO bookDAO = new BookDAOImpl();
	
	public User getUserByUserName(String username) {
		return userDAO.getUser(username);
	}
	
	public User getUserWithTrades(String username) {
//		获取 username 请求参数的值
//		调用 UserService 的 getUser(username) 获取User 对象：要求 trades 是被装配好的，而且每一个 Trade 对象的 items 也被装配好
//		具体：
//			1.调用 UserDAO 的方法获取 User 对象
//			2.调用 TradeDAO 的方法获取 Trade 的集合，把其装配为 User 的属性
//			3.调用 TradeItemDAO 的方法获取每一个 Trade 中的 TradeItem 的集合，并把其装配为 Trade 的属性
//		把 User 对象放入到 request 中
//		转发页面到 /WEB-INF/pages/trades.jsp
//			1.获取 User
//			2.遍历 User 的 Trade 集合
//			3.遍历 Trade 的 TradeItem 的集合
		
		/**
		 * 	User --> Trade --> TradeItem <-- Book
		 * 							|
		 *  User <-- Trade <-- TradeItem				
		 */
		User user = userDAO.getUser(username);
		
		if(user == null) {
			return null;
		}
		
		int userId = user.getUserId();
		
		//根据 userId 获取 Trade 类型的 Set 集合
		Set<Trade> trades = tradeDAO.getTradesWithUserId(userId);
		
		if(trades != null) {
			for(Trade trade: trades) {
				int tradeId = trade.getTradeId();
				
				//根据遍历 Trade 类型的 Set 集合获取到的 tradeId, 获取 TradeItem 类型的 Set 集合
				Set<TradeItem> items = tradeItemDAO.getTradeItemsWithTradeId(tradeId);
				if(items != null) {
					//根据遍历 TradeItem 对象获取到的 bookId, 获取 Book 对象
					for(TradeItem item: items) {
						item.setBook(bookDAO.getBook(item.getBookId()));
					}
				}
				
				trade.setItems(items);
			}
		}

		user.setTrades(trades);
		return user;
	}
}
