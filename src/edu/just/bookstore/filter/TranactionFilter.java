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
			//1. 获取连接
			
			//2. 开启事务
			
			//3. 利用 ThreadLocal 把连接和当前线程绑定
			
			//4. 把请求转给目标 Servlet
			chain.doFilter(request, response);
			
			//5. 提交事务
		} catch (Exception e) {
			e.printStackTrace();
			
			//6. 回滚事务
		} finally {
			//7. 关闭事务
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	
	}

}
