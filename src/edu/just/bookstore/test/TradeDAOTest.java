package edu.just.bookstore.test;

import java.sql.Date;
import java.util.Set;

import org.junit.jupiter.api.Test;

import edu.just.bookstore.dao.TradeDAO;
import edu.just.bookstore.dao.impl.TradeDAOImpl;
import edu.just.bookstore.domain.Trade;

class TradeDAOTest {

	private TradeDAO tradeDAO = new TradeDAOImpl();
	
	@Test
	void testInsertTrade() {
		Trade trade = new Trade();
		trade.setUserId(2);
		trade.setTradeTime(new Date(new java.util.Date().getTime()));
		
		tradeDAO.insert(trade);
	}

	@Test
	void testGetTradesWithUserId() {
		Set<Trade> set = tradeDAO.getTradesWithUserId(1);
		System.out.println(set);
	}

}
