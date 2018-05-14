package edu.just.bookstore.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.just.bookstore.dao.BookDAO;
import edu.just.bookstore.domain.Book;
import edu.just.bookstore.domain.ShoppingCartItem;
import edu.just.bookstore.web.CriteriaBook;
import edu.just.bookstore.web.Page;

public class BookDAOImpl extends BaseDAO<Book> implements BookDAO{

	@Override
	public Book getBook(int id) {
		String sql = "SELECT id, author, title, price, publishingdate, salesamount, "
				+ "storenumber, remark FROM mybooks WHERE id = ?";
		
		return query(sql, id);
	}

	//3. ����ҳ��Ϣ��װ�� Page ����, ���� Page �Ķ���
	@Override
	public Page<Book> getPage(CriteriaBook cb) {
		Page<Book> page = new Page<>(cb.getPageNo());
		
		page.setTotalItemNumber(getTotalBookNumber(cb)); 		//�����ܵļ�¼��
		//У�� pageNo �ĺϷ���
		cb.setPageNo(page.getPageNo());
		page.setList(getPageList(cb, 5));

		return page;
	}

	//1. ��ȡ�����������ܵ��鼮����Ŀ
	@Override
	public long getTotalBookNumber(CriteriaBook cb) {
		String sql = "SELECT count(id) FROM mybooks WHERE price >= ? AND price <= ?";
		
		return getSingleVal(sql, cb.getMinPrice(), cb.getMaxPrice());
	}

	/**
	 * pageSize = 5
	 * 
	 * �� 1 ҳ	5 �� 	limit 0, 5   
	 * �� 2 ҳ 	5 ��     limit 5, 5
	 * �� 3 ҳ 5 ��     limit 10,5
	 */
	//2. �����ϲ�ѯ�������鼮���з�ҳ
	@Override
	public List<Book> getPageList(CriteriaBook cb, int pageSize) {
		String sql = "SELECT id, author, title, price, publishingdate, salesamount, "
				+ "storenumber, remark FROM mybooks WHERE price >= ? AND price <= ? "
				+ "LIMIT ?, ?";
		
		return queryForList(sql, cb.getMinPrice(), cb.getMaxPrice(), (cb.getPageNo() - 1) * pageSize, pageSize);
	}

	@Override
	public int getStoreNumber(Integer id) {
		String sql = "SELECT storenumber FROM mybooks WHERE id = ?";
		return getSingleVal(sql, id);
	}

	//���� storeNumber �� salesAmount ����������
	@Override
	public void batchUpdateStoreNumberAndSalesAmount(
			Collection<ShoppingCartItem> items) {
		String sql = "UPDATE mybooks SET salesAmount = salesAmount + ?, "
				+ "storeNumber = storeNumber - ? WHERE id = ?";
		
		Object [][]params = null;
		//һ���� items.size() �������ļ�, ÿ�������ļ����� 3 ������
		params = new Object[items.size()][3];
		List<ShoppingCartItem> sc = new ArrayList<>(items);
		for(int i=0; i<items.size(); i++) {
			params[i][0] = sc.get(i).getQuantity();
			params[i][1] = sc.get(i).getQuantity();
			params[i][2] = sc.get(i).getBook().getId();
		}
		
		batch(sql, params);
	}

}
