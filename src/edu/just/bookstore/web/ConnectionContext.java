package edu.just.bookstore.web;

import java.sql.Connection;

/**
 * �÷���ʹ��ÿ�� BaseDAO �����ÿ����ɾ�Ĳ鷽��ʹ�õ���ͬһ�� Connection ����
 * @author luwen
 *
 */
public class ConnectionContext {
	
	private ConnectionContext() {}
	
	private static ConnectionContext instance = new ConnectionContext();
	
	public static ConnectionContext getInstance() {
		return instance;
	}
	
	private ThreadLocal<Connection> connectionThreadLocal = 
			new ThreadLocal<>();
	
	public void bind(Connection connection) {
		connectionThreadLocal.set(connection);
	}
	
	public Connection get() {
		return connectionThreadLocal.get();
	}
	
	public void remove() {
		connectionThreadLocal.remove();
	}
	
}
