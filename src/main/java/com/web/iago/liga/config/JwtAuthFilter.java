package com.web.iago.liga.config;

import com.web.iago.liga.provider.UserAuthenticationProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthFilter extends OncePerRequestFilter {
    public static final String ATTRIBUTE_PREFIX = "Bearer ";

    private final UserAuthenticationProvider provider;

    public JwtAuthFilter(UserAuthenticationProvider provider) {
        this.provider = provider;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith(ATTRIBUTE_PREFIX)) {
            filterChain.doFilter(request, response);
        }

        try {
            SecurityContextHolder.getContext().setAuthentication(provider.validateToken(header.substring(7)));

        } catch (RuntimeException e) {
            SecurityContextHolder.clearContext();
            throw e;
        }

        filterChain.doFilter(request, response);
    }
}
