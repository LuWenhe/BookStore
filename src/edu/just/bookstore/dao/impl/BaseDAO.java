package edu.just.bookstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import edu.just.bookstore.dao.Dao;
import edu.just.bookstore.db.JDBCUtils;
import edu.just.bookstore.utils.ReflectionUtils;

public class BaseDAO<T> implements Dao<T> {

	private QueryRunner queryRunner = new QueryRunner();
	
	private Class<T> clazz;
	
	public BaseDAO() {
		System.out.println(getClass());
		clazz = ReflectionUtils.getSuperGenericType(getClass());
		System.out.println(clazz);
	}
	
	@Override
	public long insert(String sql, Object... args) {
		
		long id = 0;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = JDBCUtils.getConnection();
			preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			if(args != null) {
				for(int i=0; i<args.length; i++) {
					preparedStatement.setObject(i + 1, args[i]);
				} 
			}
			
			preparedStatement.executeUpdate();
			
			//获取生成的主键值
			resultSet = preparedStatement.getGeneratedKeys();
			if(resultSet.next()) {
				id = resultSet.getLong(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.release(resultSet, preparedStatement);
			JDBCUtils.release(connection);
		}
		
 		return id;
	}

	@Override
	public void update(String sql, Object... args) {
		Connection connection = null;
		
		try {
			connection = JDBCUtils.getConnection();
			queryRunner.update(connection, sql, args);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.release(connection);
		}
	}

	/**
	 * 查询操作, 并且获取某个对象, 需要使用反射
	 */
	@Override
	public T query(String sql, Object... args) {
		Connection connection = null;
		
		try {
			connection = JDBCUtils.getConnection();
			return queryRunner.query(connection, sql, new BeanHandler<>(clazz), args);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.release(connection);
		}
		
		return null;
	}

	@Override
	public List<T> queryForList(String sql, Object... args) {
		Connection connection = null;
		
		try {
			connection = JDBCUtils.getConnection();
			return queryRunner.query(connection, sql, new BeanListHandler<>(clazz), args);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.release(connection);
		}
		return null;
	}

	@Override
	public <V> V getSingleVal(String sql, Object... args) {
		Connection connection = null;
		
		try {
			connection = JDBCUtils.getConnection();
			return queryRunner.query(connection, sql, new ScalarHandler<V>(), args);
					
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.release(connection);
		}
		return null;
	}

	@Override
	public void batch(String sql, Object[]... params) {
		Connection connection = null;
		
		try {
			connection = JDBCUtils.getConnection();
			queryRunner.batch(connection, sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.release(connection);
		}
		
	}

}
