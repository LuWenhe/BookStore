package edu.just.bookstore.web;

import java.util.List;

/**
 * 封装翻页信息的 Page 类
 */
public class Page<T> {

	//当前第几页
	private int pageNo;
	
	//当前页的 List
	private List<T> list;
	
	//每页显示多少条记录
	private int pageSize = 5;
	
	//共有多少条记录
	private long totalItemNumber;

	//构造器中需要对 pageNo 初始化
	public Page(int pageNo) {
		this.pageNo = pageNo;
	}
	
	//需要校验一下
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
	
	//设置总的记录数
	public void setTotalItemNumber(long totalItemNumber) {
		this.totalItemNumber = totalItemNumber;
	}

	//获取总页数
	public int getTotalPageNumber() {
		//totalItemNumber: 显示共有多少条记录
		int totalPageNumber = (int)totalItemNumber / pageSize;
		
		//如果总条数除以总页数不等于 0
		if(totalItemNumber % pageSize != 0) {
			totalPageNumber++;
		}
		
		return totalPageNumber;
	}
	
	//判断是否有上一页
	public boolean isHasNext() {
		if(getPageNo() < getTotalPageNumber()) {
			return true;
		}
		
		return false;
	}
	
	//判断是否有下一页
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
