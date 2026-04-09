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
    private static final String ORDER_SUCCESS_VIEW = "/WEB-INF/views/user/order-success.jsp";
    private static final String CART_VIEW = "/WEB-INF/views/user/cart.jsp";

    private final OrderService orderService = new OrderService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        List<CartItem> cart = getCart(session);
        String customerName = request.getParameter("customerName");

        if (isBlank(customerName)) {
            request.setAttribute("error", "Customer name is required.");
            request.getRequestDispatcher(CART_VIEW).forward(request, response);
            return;
        }

        if (cart.isEmpty()) {
            request.setAttribute("error", "Your cart is empty.");
            request.getRequestDispatcher(CART_VIEW).forward(request, response);
            return;
        }

        int orderId = orderService.placeOrder(customerName.trim(), cart);

        clearCart(session);
        request.setAttribute("orderId", orderId);
        request.getRequestDispatcher(ORDER_SUCCESS_VIEW).forward(request, response);
    }

    @SuppressWarnings("unchecked")
    private List<CartItem> getCart(HttpSession session) {
        Object value = session.getAttribute("cart");
        if (value == null) {
            return Collections.emptyList();
        }
        return (List<CartItem>) value;
    }

    private void clearCart(HttpSession session) {
        session.removeAttribute("cart");
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}