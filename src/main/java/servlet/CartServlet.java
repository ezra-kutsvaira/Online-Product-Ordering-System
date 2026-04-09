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
public class CartServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String CART_VIEW = "/WEB-INF/views/user/cart.jsp";

    private final ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(CART_VIEW).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession();
        List<CartItem> cart = getCart(session);
        String action = request.getParameter("action");

        try {
            if ("remove".equals(action)) {
                removeFromCart(request, response, cart, session);
                return;
            }

            addToCart(request, response, cart, session);

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid product id or quantity.");
            request.getRequestDispatcher(CART_VIEW).forward(request, response);
        }
    }

    private void addToCart(HttpServletRequest request, HttpServletResponse response,
                           List<CartItem> cart, HttpSession session) throws IOException {

        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        if (quantity <= 0) {
            throw new NumberFormatException("Quantity must be greater than zero");
        }

        Product product = productService.getProductById(productId);

        CartItem existingItem = findCartItem(cart, productId);

        if (existingItem == null) {
            cart.add(new CartItem(product.getId(), product.getName(), product.getPrice(), quantity));
        } else {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        }

        session.setAttribute("cart", cart);
        response.sendRedirect(request.getContextPath() + "/cart");
    }

    private void removeFromCart(HttpServletRequest request, HttpServletResponse response,
                                List<CartItem> cart, HttpSession session) throws IOException {

        int productId = Integer.parseInt(request.getParameter("productId"));
        cart.removeIf(item -> item.getProductId() == productId);

        session.setAttribute("cart", cart);
        response.sendRedirect(request.getContextPath() + "/cart");
    }

    private CartItem findCartItem(List<CartItem> cart, int productId) {
        return cart.stream()
                .filter(item -> item.getProductId() == productId)
                .findFirst()
                .orElse(null);
    }

    @SuppressWarnings("unchecked")
    private List<CartItem> getCart(HttpSession session) {
        Object value = session.getAttribute("cart");

        if (value == null) {
            List<CartItem> newCart = new ArrayList<>();
            session.setAttribute("cart", newCart);
            return newCart;
        }

        return (List<CartItem>) value;
    }
}