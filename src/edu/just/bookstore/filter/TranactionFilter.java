package edu.just.bookstore.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter("/*")
public class TranactionFilter implements Filter {

    public TranactionFilter() {
   
    }

	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			//1. ��ȡ����
			
			//2. ��������
			
			//3. ���� ThreadLocal �����Ӻ͵�ǰ�̰߳�
			
			//4. ������ת��Ŀ�� Servlet
			chain.doFilter(request, response);
			
			//5. �ύ����
		} catch (Exception e) {
			e.printStackTrace();
			
			//6. �ع�����
		} finally {
			//7. �ر�����
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	
	}

}
