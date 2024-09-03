package com.openhub.authmicroservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openhub.authmicroservice.exceptionhandler.ResponseUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class JwtFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Autowired
    public JwtFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            final String token = authHeader.substring(7);

            try {
                final String username = jwtUtil.extractUsername(token);

                // Check if session already exists
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    // Validate the token
                    if (jwtUtil.validateToken(token, username)) {
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken
                                (username, null, null);

                        // Add to security context
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            }
            catch (Exception e) {
                System.out.println(e.getMessage());

                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json");

                // Build a JSON response
                Map<String, Object> responseData = new HashMap<>();
                responseData.put("status", "error");
                responseData.put("message", "The token submitted has expired");
                responseData.put("error", e.getMessage());

                ObjectMapper objectMapper = new ObjectMapper();
                String jsonResponse = objectMapper.writeValueAsString(responseData);

                response.getWriter().write(jsonResponse);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
