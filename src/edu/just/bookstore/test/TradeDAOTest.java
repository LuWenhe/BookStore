package edu.just.bookstore.test;

import java.sql.Connection;
import java.sql.Date;
import java.util.Set;

import org.junit.jupiter.api.Test;

import edu.just.bookstore.dao.TradeDAO;
import edu.just.bookstore.dao.impl.TradeDAOImpl;
import edu.just.bookstore.db.JDBCUtils;
import edu.just.bookstore.domain.Trade;
import edu.just.bookstore.web.ConnectionContext;

class TradeDAOTest {

	private TradeDAO tradeDAO = new TradeDAOImpl();
	
	private Connection connection = JDBCUtils.getConnection();
	
	@Test
	void testInsertTrade() {
		ConnectionContext.getInstance().bind(connection);
		
		Trade trade = new Trade();
		trade.setUserId(2);
		trade.setTradeTime(new Date(new java.util.Date().getTime()));
		
		tradeDAO.insert(trade);
	}

	@Test
	void testGetTradesWithUserId() {
		ConnectionContext.getInstance().bind(connection);
		
		Set<Trade> set = tradeDAO.getTradesWithUserId(1);
		System.out.println(set);
	}

}
