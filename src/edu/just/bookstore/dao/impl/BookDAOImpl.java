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

	//3. 将分页信息封装在 Page 类中, 返回 Page 的对象
	@Override
	public Page<Book> getPage(CriteriaBook cb) {
		Page<Book> page = new Page<>(cb.getPageNo());
		
		page.setTotalItemNumber(getTotalBookNumber(cb)); 		//设置总的记录数
		//校验 pageNo 的合法性
		cb.setPageNo(page.getPageNo());
		page.setList(getPageList(cb, 5));

		return page;
	}

	//1. 获取符合条件的总的书籍的数目
	@Override
	public long getTotalBookNumber(CriteriaBook cb) {
		String sql = "SELECT count(id) FROM mybooks WHERE price >= ? AND price <= ?";
		
		return getSingleVal(sql, cb.getMinPrice(), cb.getMaxPrice());
	}

	/**
	 * pageSize = 5
	 * 
	 * 第 1 页	5 条 	limit 0, 5   
	 * 第 2 页 	5 条     limit 5, 5
	 * 第 3 页 5 条     limit 10,5
	 */
	//2. 将符合查询条件的书籍进行分页
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

	//更新 storeNumber 和 salesAmount 这两个属性
	@Override
	public void batchUpdateStoreNumberAndSalesAmount(
			Collection<ShoppingCartItem> items) {
		String sql = "UPDATE mybooks SET salesAmount = salesAmount + ?, "
				+ "storeNumber = storeNumber - ? WHERE id = ?";
		
		Object [][]params = null;
		//一共有 items.size() 个批量文件, 每个批量文件传入 3 个参数
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
