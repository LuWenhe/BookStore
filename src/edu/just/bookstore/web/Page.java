package edu.just.bookstore.web;

import java.util.List;

/**
 * ��װ��ҳ��Ϣ�� Page ��
 */
public class Page<T> {

	//��ǰ�ڼ�ҳ
	private int pageNo;
	
	//��ǰҳ�� List
	private List<T> list;
	
	//ÿҳ��ʾ��������¼
	private int pageSize = 5;
	
	//���ж�������¼
	private long totalItemNumber;

	//����������Ҫ�� pageNo ��ʼ��
	public Page(int pageNo) {
		this.pageNo = pageNo;
	}
	
	//��ҪУ��һ��
	public int getPageNo() {
		if(pageNo < 0) {
			pageNo = 1;
		}
		
		if(pageNo > getTotalPageNumber()) {
			pageNo = getTotalPageNumber();
		}
		
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	public void setList(List<T> list) {
		this.list = list;
	}
	
	public List<T> getList() {
		return list;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	//�����ܵļ�¼��
	public void setTotalItemNumber(long totalItemNumber) {
		this.totalItemNumber = totalItemNumber;
	}

	//��ȡ��ҳ��
	public int getTotalPageNumber() {
		//totalItemNumber: ��ʾ���ж�������¼
		int totalPageNumber = (int)totalItemNumber / pageSize;
		
		//���������������ҳ�������� 0
		if(totalItemNumber % pageSize != 0) {
			totalPageNumber++;
		}
		
		return totalPageNumber;
	}
	
	//�ж��Ƿ�����һҳ
	public boolean isHasNext() {
		if(getPageNo() < getTotalPageNumber()) {
			return true;
		}
		
		return false;
	}
	
	//�ж��Ƿ�����һҳ
	public boolean isHasPrev() {
		if(getPageNo() > 1) {
			return true;
		}
		
		return false;
	}
	
	public int getPrevPage() {
		if(isHasPrev()) {
			return getPageNo() - 1;
		}
		
		return getPageNo();
	}
	
	public int getNextPage() {
		if(isHasNext()) {
			return getPageNo() + 1;
		}
		
		return getPageNo();
	}
	
}
