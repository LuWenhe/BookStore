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
//		��ȡ username ���������ֵ
//		���� UserService �� getUser(username) ��ȡUser ����Ҫ�� trades �Ǳ�װ��õģ�����ÿһ�� Trade ����� items Ҳ��װ���
//		���壺
//			1.���� UserDAO �ķ�����ȡ User ����
//			2.���� TradeDAO �ķ�����ȡ Trade �ļ��ϣ�����װ��Ϊ User ������
//			3.���� TradeItemDAO �ķ�����ȡÿһ�� Trade �е� TradeItem �ļ��ϣ�������װ��Ϊ Trade ������
//		�� User ������뵽 request ��
//		ת��ҳ�浽 /WEB-INF/pages/trades.jsp
//			1.��ȡ User
//			2.���� User �� Trade ����
//			3.���� Trade �� TradeItem �ļ���
		
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
		
		//���� userId ��ȡ Trade ���͵� Set ����
		Set<Trade> trades = tradeDAO.getTradesWithUserId(userId);
		
		if(trades != null) {
			for(Trade trade: trades) {
				int tradeId = trade.getTradeId();
				
				//���ݱ��� Trade ���͵� Set ���ϻ�ȡ���� tradeId, ��ȡ TradeItem ���͵� Set ����
				Set<TradeItem> items = tradeItemDAO.getTradeItemsWithTradeId(tradeId);
				if(items != null) {
					//���ݱ��� TradeItem �����ȡ���� bookId, ��ȡ Book ����
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
