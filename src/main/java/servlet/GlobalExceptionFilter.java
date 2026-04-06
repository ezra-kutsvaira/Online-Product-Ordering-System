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

@WebFilter("/")
public class GlobalExceptionFilter implements Filter {
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
        try {
            filterChain.doFilter(request, response);
        } catch (ValidationException e) {
            handle(req, res, HttpServletResponse.SC_BAD_REQUEST, "Validation Error", e.getMessage());
        } catch (NotFoundException e) {
            handle(req, res, HttpServletResponse.SC_NOT_FOUND, "Not Found", e.getMessage());
        } catch (AppException e) {
            handle(req, res, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Application Error", e.getMessage());
        } catch (Exception e) {
            handle(req, res, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unexpected Error", "Unexpected server error");
        }
    
	}
		
	private void handle(HttpServletRequest req, HttpServletResponse resp, int status, String title, String message) throws IOException, ServletException {
        resp.setStatus(status);
        req.setAttribute("errorTitle", title);
        req.setAttribute("errorMessage", message);
        req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
    }
}