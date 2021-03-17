package com.binas.tachographcms.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolderStrategy;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;

public class SecretTokenFilter extends HttpFilter {

    private AuthenticationManager authenticationManager;

    private SecurityContextHolderStrategy securityContextHolderStrategy;

    public SecretTokenFilter(AuthenticationManager authenticationManager,
                             SecurityContextHolderStrategy securityContextHolderStrategy) {
        this.authenticationManager = authenticationManager;
        this.securityContextHolderStrategy = securityContextHolderStrategy;
    }

     @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
     throws IOException, ServletException {
         String req = request.getRequestURI().substring(request.getContextPath().length());
         if (!req.equals("/android/user")) {
             String secretHeaderValue = request.getHeader("X-Secret");
             Enumeration<String> headerNames = request.getHeaderNames();
             if (secretHeaderValue == null) {
                 throw new IllegalArgumentException("X-Secret header is required.");
             }
             Authentication secretToken = new SecretToken(Collections.emptyList(), secretHeaderValue, null);
             Authentication authResult = authenticationManager.authenticate(secretToken);
             securityContextHolderStrategy.getContext().setAuthentication(authResult);
             chain.doFilter(request, response);
         }
         chain.doFilter(request, response);
     }
}
