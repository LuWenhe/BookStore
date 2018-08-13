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
		//1. ��ȡ��Ʒ�� id
		String idStr = request.getParameter("id");
		int id = -1;
		boolean flag = false;
		
		try {
			id = Integer.parseInt(idStr);
		} catch (Exception e) {}
		
		if(id > 0) {
			//2. ��ȡ���ﳵ����
			ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
			
			//3. ���� BookService �� addTocart() ��������Ʒ�ŵ����ﳵ��
			flag = bookService.addTocart(id, sc);
		}
		
		if(flag) {
			//4. ֱ�ӵ��� getBooks() ����
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
		//4. �� updateItemQuantity ������, ��ȡ quantity, id, �ڻ�ȡ���ﳵ������� service �ķ������޸�
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
		
		//5. ���� JSON ����, bookNumber: xx, totalMoney
		Map<String, Object> result = new HashMap<>();
		result.put("bookNumber", sc.getBookNumber());			//���ﳵ�е�ͼ������
		result.put("totalMoney", sc.getTotalMoney());
		
		ObjectMapper mapper = new ObjectMapper();
		String result1 = mapper.writeValueAsString(result);
		
		response.getWriter().println(result1);
	}

	public void cash(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. ����֤, ��֤�����ֵ�Ƿ���ϻ����Ĺ淶: �Ƿ�Ϊ��, �Ƿ����תΪ int ����, �Ƿ���һ�� email,
		//   ����Ҫ���в�ѯ���ݿ������κε�ҵ�񷽷�
		String username = request.getParameter("username");
		String accountId = request.getParameter("accountId");
		
		StringBuffer error = validateFormField(username, accountId);

		//����֤ͨ��, ��֤�û������˻�
		if(error.toString().equals("")) {
			error = validateUser(username, accountId);
			
			//�û������˻���֤ͨ��, ��֤���
			if(error.toString().equals("")) {
				error = validateBookStoreNumber(request);
				
				//�����֤ͨ��, ��֤�˻����
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
		
		//��֤ͨ��ִ�о�����߼�����
		bookService.cash(BookStoreWebUtils.getShoppingCart(request), username, accountId);
		
		response.sendRedirect(request.getContextPath() + "/success.jsp");
	}
	
	//��֤�����Ƿ���ϻ����Ĺ��: �Ƿ�Ϊ��
	public StringBuffer validateFormField(String username, String accountId) {
		StringBuffer error = new StringBuffer("");
		
		if(username == null || username.trim().equals("")) {
			error.append("�û�������Ϊ��");
		}
		
		if(accountId == null || accountId.trim().equals("")) {
			error.append(",���벻��Ϊ��");
		}
		
		return error;
	}
	
	//��֤�û������ʻ����Ƿ�ƥ��
	public StringBuffer validateUser(String username, String accountId) {
		//2. �����˻��������Ƿ�ƥ��
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
			error2.append("�˻������벻ƥ��");
		}
		
		return error2;
	}
	
	//��֤����Ƿ����
	public StringBuffer validateBookStoreNumber(HttpServletRequest request) {
		ShoppingCart cart = BookStoreWebUtils.getShoppingCart(request);
		
		StringBuffer error = new StringBuffer("");
		
		for(ShoppingCartItem sci: cart.getItems()) {
			int quautity = sci.getQuantity();
			int storNumber = bookService.getBook(sci.getBook().getId()).getStoreNumber();
			
			if(quautity > storNumber) {
				error.append(sci.getBook().getTitle() + "��治��<br/>");
			}
		}
		
		return error;
		
	}
	
	//��֤����Ƿ����
	public StringBuffer validateBalance(HttpServletRequest request, String accountId) {
		ShoppingCart cart = BookStoreWebUtils.getShoppingCart(request);
		StringBuffer error = new StringBuffer("");
		
		Account account = accountService.getAccount(Integer.parseInt(accountId));
		//�û��˻��е��ܽ��
		float balance = account.getBalance();
		
		//���ﳵ��������Ʒ���ܽ��
		float totalMoney = cart.getTotalMoney();
		
		System.out.println("B:" + balance + " " + "T:" + totalMoney);
		
		if(balance < totalMoney) {
			error.append("����!");
		}
		
		return error;
	}
	
}
