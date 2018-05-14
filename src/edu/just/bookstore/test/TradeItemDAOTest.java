package edu.just.bookstore.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.junit.jupiter.api.Test;

import edu.just.bookstore.dao.TradeItemDAO;
import edu.just.bookstore.dao.impl.TradeItemDAOImpl;
import edu.just.bookstore.domain.TradeItem;

class TradeItemDAOTest {

	TradeItemDAO tradeItemDAO = new TradeItemDAOImpl();
	
	@Test
	void testBatchSave() {
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
		Set<TradeItem> set = tradeItemDAO.getTradeItemsWithTradeId(13);
		System.out.println(set);
	}

}
