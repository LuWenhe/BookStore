package edu.just.bookstore.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.just.bookstore.dao.TradeItemDAO;
import edu.just.bookstore.domain.TradeItem;

public class TradeItemDAOImpl extends BaseDAO<TradeItem> implements TradeItemDAO {

	@Override
	public void batchSave(Collection<TradeItem> items) {
		String sql = "INSERT INTO tradeitem(bookid, quantity, tradeid) "
				+ "VALUES(?,?,?)";
		
		Object [][]params = null;
		params = new Object[items.size()][3];
		List<TradeItem> items2 = new ArrayList<>(items);
		for(int i=0; i<items.size(); i++) {
			params[i][0] = items2.get(i).getBookId();
			params[i][1] = items2.get(i).getQuantity();
			params[i][2] = items2.get(i).getTradeId();
		}
	
		batch(sql, params);
	}

	//根据 tradeId 获取 tradeItem 集合
	@Override
	public Set<TradeItem> getTradeItemsWithTradeId(Integer tradeId) {
		String sql = "SELECT itemid tradeitemid, bookid, quantity, tradeid "
				+ "FROM tradeitem t where t.tradeid = ?";
		
		List<TradeItem> tradeItems = queryForList(sql, tradeId);
		Set<TradeItem> set = new HashSet<>(tradeItems);
		
		return set;
	}

}
