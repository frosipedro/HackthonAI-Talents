package com.banking.gateway.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Extract the token from the request header
        String authToken = httpRequest.getHeader("Authorization");

        // Validate the token (this is a placeholder for actual validation logic)
        if (authToken == null || !isValidToken(authToken)) {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return;
        }

        // Continue the filter chain
        chain.doFilter(request, response);
    }

    private boolean isValidToken(String token) {
        // Implement your token validation logic here
        return true; // Placeholder for demonstration
    }

    @Override
    public void destroy() {
        // Cleanup logic if needed
    }
}