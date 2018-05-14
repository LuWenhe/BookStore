package edu.just.bookstore.dao;

import java.util.Collection;
import java.util.List;

import edu.just.bookstore.domain.Book;
import edu.just.bookstore.domain.ShoppingCartItem;
import edu.just.bookstore.web.CriteriaBook;
import edu.just.bookstore.web.Page;

public interface BookDAO {

	/**
	 * ���� id ��ȡָ�� Book ����
	 * @param id
	 * @return
	 */
	public abstract Book getBook(int id);
	
	/**
	 * ���ݴ���� CriteriaBook(��ѯ Book ������) ���󷵻ض�Ӧ�� Page ����
	 * @param cb
	 * @return
	 */
	public abstract Page<Book> getPage(CriteriaBook cb);
	
	/**
	 * ���ݴ���� CriteriaBook ���󷵻����Ӧ�ļ�¼��
	 * @param cb
	 * @return
	 */
	public abstract long getTotalBookNumber(CriteriaBook cb);
	
	/**
	 * ���ݴ���� CriteriaBook �� PageSize ���ص�ǰҳ��Ӧ�� List
	 * @param cb
	 * @param pageSize
	 * @return
	 */
	public abstract List<Book> getPageList(CriteriaBook cb, int pageSize);
	
	/**
	 * ����ָ�� id �� book �� storeNumber �ֶ�
	 * @param id
	 * @return
	 */
	public abstract int getStoreNumber(Integer id);
	
	/**
	 * ���ݴ���� ShoppingCartItem �ļ���, �������� books ���ݱ�� storenumber �� salesnumber �ֶε�ֵ
	 * @param items
	 */
	public abstract void batchUpdateStoreNumberAndSalesAmount(Collection<ShoppingCartItem> items);
	
}
