package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.CartItem;
import model.Product;
import service.ProductService;

@WebServlet("/cart")
public class CartServlet extends HttpServlet{

	private final ProductService productService = new ProductService();
	 
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		 request.getRequestDispatcher("/WEB-INF/views/user/cart.jsp").forward(request, response);
	}
	
	@Override 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws  IOException{
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		List<CartItem> cart = getCart(session);
		
		
		if("remove".equals(action)) {
			int productId = Integer.parseInt(request.getParameter("productId"));
			cart.removeIf(item -> item.getProductId() == productId);
			response.sendRedirect(request.getContextPath() + "/cart");
			return; 
		}
		
		int productId = Integer.parseInt(request.getParameter("productId"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		
		Product product = productService.getProductById(productId);
		CartItem existing = cart.stream()
				.filter(item -> item.getProductId() == productId)
				.findFirst()
				.orElse(null);
		
		if(existing == null) {
			cart.add(new CartItem(product.getId(), product.getName(), product.getPrice(), quantity));
		}else {
			existing.setQuantity(existing.getQuantity() + quantity);
		}
		
		session.setAttribute("cart", cart);
		response.sendRedirect(request.getContextPath() +  "/cart");
		
	}

	 @SuppressWarnings("unchecked")
	private List<CartItem> getCart(HttpSession session) {
		Object value = session.getAttribute("cart");
		if(value == null) {
			 List<CartItem> newCart = new ArrayList<>();
	            session.setAttribute("cart", newCart);
	            return newCart;
		}
		return (List <CartItem>) value;
	}
}
