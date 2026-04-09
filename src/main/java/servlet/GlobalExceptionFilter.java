package servlet;

import java.io.IOException;

import exceptions.AppException;
import exceptions.NotFoundException;
import exceptions.ValidationException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter("/*") // apply to all requests
public class GlobalExceptionFilter implements Filter {

    private static final String ERROR_VIEW = "/WEB-INF/views/error.jsp";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        try {
            chain.doFilter(request, response);

        } catch (ValidationException e) {
            handle(req, res, HttpServletResponse.SC_BAD_REQUEST,
                    "Validation Error", e.getMessage());

        } catch (NotFoundException e) {
            handle(req, res, HttpServletResponse.SC_NOT_FOUND,
                    "Not Found", e.getMessage());

        } catch (AppException e) {
            handle(req, res, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Application Error", e.getMessage());

        } catch (Exception e) {
            // Log this in real systems
            e.printStackTrace();

            handle(req, res, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Unexpected Error", "Something went wrong. Please try again.");
        }
    }

    private void handle(HttpServletRequest req,
                        HttpServletResponse resp,
                        int status,
                        String title,
                        String message) throws IOException, ServletException {

        resp.setStatus(status);

        req.setAttribute("errorTitle", title);
        req.setAttribute("errorMessage", message);

        req.getRequestDispatcher(ERROR_VIEW).forward(req, resp);
    }
}