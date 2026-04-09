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

// Admin controller for product CRUD operations
@WebServlet("/admin/products")
public class AdminProductServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String PRODUCT_FORM_VIEW = "/WEB-INF/views/admin/product-form.jsp";
    private static final String PRODUCT_LIST_VIEW = "/WEB-INF/views/admin/product-list.jsp";

    private final ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("new".equals(action)) {
            showNewForm(request, response);
            return;
        }

        if ("edit".equals(action)) {
            showEditForm(request, response);
            return;
        }

        if ("delete".equals(action)) {
            deleteProduct(request, response);
            return;
        }

        listProducts(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        try {
            Product product = buildProductFromRequest(request);
            String id = request.getParameter("id");

            if (id == null || id.isBlank()) {
                productService.createProduct(product);
            } else {
                product.setId(Integer.parseInt(id));
                productService.updateProduct(product);
            }

            response.sendRedirect(request.getContextPath() + "/admin/products");

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid numeric input for price, stock, or id.");
            request.getRequestDispatcher(PRODUCT_FORM_VIEW).forward(request, response);
        }
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(PRODUCT_FORM_VIEW).forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        request.setAttribute("product", productService.getProductById(id));
        request.getRequestDispatcher(PRODUCT_FORM_VIEW).forward(request, response);
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        productService.deleteProduct(id);
        response.sendRedirect(request.getContextPath() + "/admin/products");
    }

    private void listProducts(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("products", productService.getAllProducts());
        request.getRequestDispatcher(PRODUCT_LIST_VIEW).forward(request, response);
    }

    private Product buildProductFromRequest(HttpServletRequest request) {
        Product product = new Product();
        product.setName(request.getParameter("name"));
        product.setDescription(request.getParameter("description"));
        product.setPrice(new BigDecimal(request.getParameter("price")));
        product.setStock(Integer.parseInt(request.getParameter("stock")));
        return product;
    }
}