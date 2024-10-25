package com.kshitij.hostel_hub.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collection;
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // Redirect based on user role
        if (authorities.stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            response.sendRedirect("/dashboard");  // Admin dashboard
        } else if (authorities.stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_USER"))) {
            response.sendRedirect("/dashboard");  // User dashboard
        } else {
            response.sendRedirect("/default");  // Default page for other roles or unauthorized access
        }
    }
}