package com.binas.tachographcms.security;

import com.binas.tachographcms.repository.AdminRepository;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminAuthenticationProvider implements AuthenticationProvider {

    private AdminRepository adminRepository;

    public AdminAuthenticationProvider(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
         if(authentication.getPrincipal() != null && authentication.getCredentials() != null) {
            String username = authentication.getPrincipal().toString();
            String password = authentication.getCredentials().toString();
            return this.adminRepository.getAdminById(1L).filter(admin -> admin.getUsername().equals(username) && admin.getPassword().equals(password)).map(admin -> {
                AbstractAuthenticationToken authResult = new UsernamePasswordAuthenticationToken(username, password, List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
                SecurityContextHolder.getContext().setAuthentication(authResult);
                return authResult;
            }).orElseThrow(() -> new BadCredentialsException("Username and/or password are incorrect."));
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.equals(aClass);
    }
}
