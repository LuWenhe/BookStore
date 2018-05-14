package edu.just.bookstore.web;

import java.sql.Connection;

public class ConnectionContext {

	private ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
	
	
}
