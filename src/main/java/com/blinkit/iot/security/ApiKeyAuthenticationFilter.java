package com.blinkit.iot.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApiKeyAuthenticationFilter extends OncePerRequestFilter {

    @Value("${api.security.client-name}")
    private String expectedClientName;

    @Value("${api.security.token}")
    private String expectedToken;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        // Skip authentication for health check and public endpoints
        if (path.contains("/actuator") || path.contains("/swagger") || path.contains("/api-docs")) {
            filterChain.doFilter(request, response);
            return;
        }

        String clientName = request.getHeader("x-client-name");
        String authToken = request.getHeader("authorization");

        if (clientName == null || authToken == null) {
            log.warn("Missing authentication headers from IP: {}", request.getRemoteAddr());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"message\":\"Unauthorized request\"}");
            return;
        }

        if (!expectedClientName.equals(clientName) || !expectedToken.equals(authToken)) {
            log.warn("Invalid authentication credentials from IP: {}", request.getRemoteAddr());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"message\":\"Unauthorized request\"}");
            return;
        }

        // Set authentication in security context
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(clientName, null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}