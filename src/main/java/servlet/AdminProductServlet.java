package servlet;

import java.io.IOException;
import java.math.BigDecimal;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Product;
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
	
	 @Override
	    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	        Product product = new Product();
	        product.setName(req.getParameter("name"));
	        product.setDescription(req.getParameter("description"));
	        product.setPrice(new BigDecimal(req.getParameter("price")));
	        product.setStock(Integer.parseInt(req.getParameter("stock")));

	        String id = req.getParameter("id");
	        if (id == null || id.isBlank()) {
	            productService.createProduct(product);
	        } else {
	            product.setId(Integer.parseInt(id));
	            productService.updateProduct(product);
	        }

	 }    
}
