package com.web.iago.liga.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.iago.liga.constants.AuthPath;
import com.web.iago.liga.dto.CredentialsDto;
import com.web.iago.liga.provider.UserAuthenticationProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class UsernamePasswordAuthFilter extends OncePerRequestFilter {
    public static final ObjectMapper MAPPER = new ObjectMapper();

    private final UserAuthenticationProvider provider;

    public UsernamePasswordAuthFilter(UserAuthenticationProvider provider) {
        this.provider = provider;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        if (AuthPath.SIGN_IN.equals(request.getServletPath()) && HttpMethod.POST.matches(request.getMethod())) {
            CredentialsDto credentialsDto = MAPPER.readValue(request.getInputStream(), CredentialsDto.class);

            try {
                SecurityContextHolder.getContext().setAuthentication(provider.validateCredentials(credentialsDto));

            } catch (RuntimeException e) {
                SecurityContextHolder.clearContext();
                throw e;
            }
        }

        filterChain.doFilter(request, response);
    }
}