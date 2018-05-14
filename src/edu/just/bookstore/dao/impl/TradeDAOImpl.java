package edu.just.bookstore.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.just.bookstore.dao.TradeDAO;
import edu.just.bookstore.domain.Trade;

public class TradeDAOImpl extends BaseDAO<Trade> implements TradeDAO {

	@Override
	public void insert(Trade trade) {
		String sql = "INSERT INTO trade(userid, tradetime) "
				+ "VALUES(?, ?)";
		
		long tradeId = insert(sql, trade.getUserId(), trade.getTradeTime());
		trade.setTradeId((int)tradeId);
	}

	//���� userId ��ȡ trade �ļ���
	@Override
	public Set<Trade> getTradesWithUserId(Integer userId) {
		String sql = "SELECT tradeid, userid, tradetime FROM trade "
				+ "WHERE userid = ?";
		List<Trade> list = queryForList(sql, userId);
		Set<Trade> set = new HashSet<>(list);
		return set;
	}

}
