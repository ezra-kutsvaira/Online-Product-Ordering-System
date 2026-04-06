package servlet;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.CartItem;
import service.OrderService;


@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	public final OrderService orderService = new OrderService ();
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//Initialising a session
		HttpSession session = request.getSession();
		
		@SuppressWarnings("unchecked")
		List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
		if(cart == null) {
			cart = Collections.emptyList(); //immutable - safe default
		}
		
		String customerName = request.getParameter("customerName");
		int orderId = orderService.placeOrder(customerName, cart);
		
		session.removeAttribute("cart");
		request.setAttribute("orderId", orderId);
		request.getRequestDispatcher("/WEB-INF/views/user/order-success.jsp").forward(request, response);
	}

}
