package servlet;

import java.io.IOException;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ProductService;

@WebServlet({"/" , "/products"})
public class ProductCatalogueServlet extends HttpServlet {
	

	private static final long serialVersionUID = 1L;
	
	private final ProductService productService = new ProductService();
	
	@Override
	 protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        req.setAttribute("products", productService.getAllProducts());
	        req.getRequestDispatcher("/WEB-INF/views/user/product-list.jsp").forward(req, resp);
	    }

}
