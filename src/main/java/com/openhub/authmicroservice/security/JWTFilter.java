package com.openhub.authmicroservice.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Configuration
public class JWTFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private JWTUtil jwtutil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Cast the Servlet request/response to HttpServlet req/res
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        final String authHeader = req.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            final String token = authHeader.substring(7);
            final String username = jwtutil.extractUsername(token);

            // Check if the user session already exists
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // Validate JWT token
                if (jwtutil.validateToken(token, username)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken
                            (username, null, null);
                    // Set the authentication in Security context
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        // Proceed to the next filter
        chain.doFilter(request, response);
    }
}
