package edu.just.bookstore.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.just.bookstore.db.JDBCUtils;
import edu.just.bookstore.web.ConnectionContext;

@WebFilter("/*")
public class TranactionFilter implements Filter {

    public TranactionFilter() {
   
    }

	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		Connection connection = null;
		
		try {
			//1. ��ȡ����
			connection = JDBCUtils.getConnection();
			
			//2. ��������
			connection.setAutoCommit(false);
			
			//3. ���� ThreadLocal �����Ӻ͵�ǰ�̰߳�
			ConnectionContext.getInstance().bind(connection);
			
			//4. ������ת��Ŀ�� Servlet
			chain.doFilter(request, response);
			
			//5. �ύ����
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			
			//6. �ع�����
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse res	 = (HttpServletResponse) response;
			res.sendRedirect(req.getContextPath() + "/error-1.jsp");
			
		} finally {
			//7. �����
			ConnectionContext.getInstance().remove();
			
			//7. �ر�����
			JDBCUtils.release(connection);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	
	}

}
