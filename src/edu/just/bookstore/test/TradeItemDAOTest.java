package edu.just.bookstore.test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.junit.jupiter.api.Test;

import edu.just.bookstore.dao.TradeItemDAO;
import edu.just.bookstore.dao.impl.TradeItemDAOImpl;
import edu.just.bookstore.db.JDBCUtils;
import edu.just.bookstore.domain.TradeItem;
import edu.just.bookstore.web.ConnectionContext;

class TradeItemDAOTest {

	TradeItemDAO tradeItemDAO = new TradeItemDAOImpl();
	
	private Connection connection = JDBCUtils.getConnection();
	
	@Test
	void testBatchSave() {
		ConnectionContext.getInstance().bind(connection);
		
		Collection<TradeItem> items = new ArrayList<>();
		
		TradeItem tradeItem = new TradeItem();
		tradeItem.setBookId(25);
		tradeItem.setQuantity(10);
		tradeItem.setTradeId(17);
		items.add(tradeItem);

		tradeItem = new TradeItem();
		tradeItem.setBookId(25);
		tradeItem.setQuantity(10);
		tradeItem.setTradeId(17);
		items.add(tradeItem);
		
		tradeItem = new TradeItem();
		tradeItem.setBookId(25);
		tradeItem.setQuantity(10);
		tradeItem.setTradeId(17);
		items.add(tradeItem);

		tradeItemDAO.batchSave(items);
	}

	@Test
	void testGetTradeItemsWithTradeId() {
		ConnectionContext.getInstance().bind(connection);
		
		Set<TradeItem> set = tradeItemDAO.getTradeItemsWithTradeId(13);
		System.out.println(set);
	}

}
