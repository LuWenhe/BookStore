package edu.just.bookstore.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.just.bookstore.domain.User;
import edu.just.bookstore.service.UserService;

@WebServlet("/userServlet")
public class UserServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	private UserService userService = new UserService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String methodName = request.getParameter("method");
		
		try {
			Method method = getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			method.invoke(this, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		User user = userService.getUserWithTrades(username);
		
		if(user == null) {
			response.sendRedirect(request.getServletPath() + "/error-1.jsp");
		}
		
		request.setAttribute("user", user);
		
		request.getRequestDispatcher("/WEB-INF/pages/trades.jsp").forward(request, response);
	}
}
