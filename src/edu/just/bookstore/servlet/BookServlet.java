package edu.just.bookstore.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.just.bookstore.domain.Account;
import edu.just.bookstore.domain.Book;
import edu.just.bookstore.domain.ShoppingCart;
import edu.just.bookstore.domain.ShoppingCartItem;
import edu.just.bookstore.domain.User;
import edu.just.bookstore.service.AccountService;
import edu.just.bookstore.service.BookService;
import edu.just.bookstore.service.UserService;
import edu.just.bookstore.web.BookStoreWebUtils;
import edu.just.bookstore.web.CriteriaBook;
import edu.just.bookstore.web.Page;

@WebServlet("/bookServlet")
public class BookServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	private BookService bookService = new BookService();
	
	private UserService userService = new UserService();
	
	private AccountService accountService = new AccountService();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String methodName = request.getParameter("method");
		
		try {
			Method method = getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			method.setAccessible(true);
			method.invoke(this, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public void forwardPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = request.getParameter("page");
		request.getRequestDispatcher("/WEB-INF/pages/" + page + ".jsp").forward(request, response);
	}

	public void addTocart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 获取商品的 id
		String idStr = request.getParameter("id");
		int id = -1;
		boolean flag = false;
		
		try {
			id = Integer.parseInt(idStr);
		} catch (Exception e) {}
		
		if(id > 0) {
			//2. 获取购物车对象
			ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
			
			//3. 调用 BookService 的 addTocart() 方法把商品放到购物车中
			flag = bookService.addTocart(id, sc);
		}
		
		if(flag) {
			//4. 直接调用 getBooks() 方法
			getBooks(request, response);
			return;
		}
		
		response.sendRedirect(request.getContextPath() + "/error-1.jsp");
	}
	
	public void getBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		int id = -1;

		Book book = null;
		
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {}
		
		if(id > 0) {
			book = bookService.getBook(id);
		}
		
		if(book == null) {
			response.sendRedirect(request.getContextPath() + "/error-1.jsp");
			return;
		}
		
		request.setAttribute("book", book);
		request.getRequestDispatcher("/WEB-INF/pages/book.jsp").forward(request, response);
	}

	public void getBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pageNoStr = request.getParameter("pageNo");
		String minPriceStr = request.getParameter("minPrice");
		String maxPriceStr = request.getParameter("maxPrice");
		
		int pageNo = 1;
		int minPrice = 0;
		int maxPrice = Integer.MAX_VALUE;
		
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {}
		
		try {
			minPrice = Integer.parseInt(minPriceStr);
		} catch (NumberFormatException e) {}
		
		try {
			maxPrice = Integer.parseInt(maxPriceStr);
		} catch (NumberFormatException e) {}
		
		CriteriaBook criteriaBook = new CriteriaBook(minPrice, maxPrice, pageNo);
		
		Page<Book> page = bookService.getPage(criteriaBook);
		
		request.setAttribute("bookPage", page);
		
		request.getRequestDispatcher("/WEB-INF/pages/books.jsp").forward(request, response);
	}
 
	public void remove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		int id = -1;
		
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {}
		
		if(id > 0) {
			ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
			bookService.removeItemFromShoppingCart(id, sc);
			
			if(sc.isEmpty()) {
				request.getRequestDispatcher("/WEB-INF/pages/emptycart.jsp").forward(request, response);
			}
		}
		
		request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
	}
	
	public void clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		bookService.clear(sc);
		
		request.getRequestDispatcher("/WEB-INF/pages/emptycart.jsp").forward(request, response);
	}

	public void updateItemQuantity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//4. 在 updateItemQuantity 方法中, 获取 quantity, id, 在获取购物车对象调用 service 的方法作修改
		String idStr = request.getParameter("id");
		String quantityStr = request.getParameter("quantity");
		
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		
		int id = -1;
		int quantity = -1;
		
		try {
			id = Integer.parseInt(idStr);
			quantity = Integer.parseInt(quantityStr);
		} catch (Exception e) {}
		
		if(id > 0 && quantity > 0) {
			bookService.updateItemQuantity(sc, id, quantity);
		}
		
		//5. 传回 JSON 数据, bookNumber: xx, totalMoney
		Map<String, Object> result = new HashMap<>();
		result.put("bookNumber", sc.getBookNumber());			//购物车中的图书总量
		result.put("totalMoney", sc.getTotalMoney());
		
		ObjectMapper mapper = new ObjectMapper();
		String result1 = mapper.writeValueAsString(result);
		
		response.getWriter().println(result1);
	}

	public void cash(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 简单验证, 验证表单域的值是否符合基本的规范: 是否为空, 是否可以转为 int 类型, 是否是一个 email,
		//   不需要进行查询数据库或调用任何的业务方法
		String username = request.getParameter("username");
		String accountId = request.getParameter("accountId");
		
		StringBuffer error = validateFormField(username, accountId);

		//表单验证通过, 验证用户名和账户
		if(error.toString().equals("")) {
			error = validateUser(username, accountId);
			
			//用户名和账户验证通过, 验证库存
			if(error.toString().equals("")) {
				error = validateBookStoreNumber(request);
				
				//库存验证通过, 验证账户余额
				if(error.toString().equals("")) {
					error = validateBalance(request, accountId);
				}
			}
		}
		
		if(!error.toString().equals("")) {
			request.setAttribute("errors", error);
			request.setAttribute("username", username);
			request.getRequestDispatcher("/WEB-INF/pages/cash.jsp").forward(request, response);
			return;
		}
		
		//验证通过执行具体的逻辑操作
		bookService.cash(BookStoreWebUtils.getShoppingCart(request), username, accountId);
		
		response.sendRedirect(request.getContextPath() + "/success.jsp");
	}
	
	//验证表单域是否符合基本的规格: 是否为空
	public StringBuffer validateFormField(String username, String accountId) {
		StringBuffer error = new StringBuffer("");
		
		if(username == null || username.trim().equals("")) {
			error.append("用户名不能为空");
		}
		
		if(accountId == null || accountId.trim().equals("")) {
			error.append(",密码不能为空");
		}
		
		return error;
	}
	
	//验证用户名和帐户名是否匹配
	public StringBuffer validateUser(String username, String accountId) {
		//2. 检验账户和密码是否匹配
		boolean flag = false;
		User user = userService.getUserByUserName(username);
		if(user != null) {
			int accountId2 = user.getAccountId();
			if(accountId.trim().equals("" + accountId2)) {
				flag = true;
			}
		} 

		StringBuffer error2 = new StringBuffer("");
		
		if(!flag) {
			error2.append("账户和密码不匹配");
		}
		
		return error2;
	}
	
	//验证库存是否充足
	public StringBuffer validateBookStoreNumber(HttpServletRequest request) {
		ShoppingCart cart = BookStoreWebUtils.getShoppingCart(request);
		
		StringBuffer error = new StringBuffer("");
		
		for(ShoppingCartItem sci: cart.getItems()) {
			int quautity = sci.getQuantity();
			int storNumber = bookService.getBook(sci.getBook().getId()).getStoreNumber();
			
			if(quautity > storNumber) {
				error.append(sci.getBook().getTitle() + "库存不足<br/>");
			}
		}
		
		return error;
		
	}
	
	//验证余额是否充足
	public StringBuffer validateBalance(HttpServletRequest request, String accountId) {
		ShoppingCart cart = BookStoreWebUtils.getShoppingCart(request);
		StringBuffer error = new StringBuffer("");
		
		Account account = accountService.getAccount(Integer.parseInt(accountId));
		//用户账户中的总金额
		float balance = account.getBalance();
		
		//购物车中所有商品的总金额
		float totalMoney = cart.getTotalMoney();
		
		System.out.println("B:" + balance + " " + "T:" + totalMoney);
		
		if(balance < totalMoney) {
			error.append("余额不足!");
		}
		
		return error;
	}
	
}
