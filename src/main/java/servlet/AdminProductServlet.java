package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ProductService;

// Admin controller for product CRUD Operations
@WebServlet("/admin/products")
public class AdminProductServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private final ProductService productService = new ProductService();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String action = request.getParameter("action");
		
		if ("new".equals(action)) {
			request.getRequestDispatcher("/WEB-INF/views/admin/product-form.jsp").forward(request, response);
			return;
		}
		
		if("edit".equals(action)) {
			int id = Integer.parseInt(request.getParameter("id"));
			request.setAttribute("product", productService.getProductById(id));
			request.getRequestDispatcher("/WEB-INF/views/admin/product-form.jsp").forward(request, response);
			return;
		}
		
		if("delete".equals("action")) {
			int id = Integer.parseInt(request.getParameter("id"));
			productService.deleteProduct(id);
			response.sendRedirect(request.getContextPath() + "/admin/products");
			return;
		}
		
		request.setAttribute("products", productService.getAllProducts());
        request.getRequestDispatcher("/WEB-INF/views/admin/product-list.jsp").forward(request, response);
		
	}
}
