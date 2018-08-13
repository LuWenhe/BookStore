package edu.just.bookstore.web;

import java.sql.Connection;

/**
 * 该方法使得每个 BaseDAO 里面的每个增删改查方法使用的是同一个 Connection 对象
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
