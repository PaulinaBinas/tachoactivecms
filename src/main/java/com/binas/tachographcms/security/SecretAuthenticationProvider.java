package com.binas.tachographcms.security;

import com.binas.tachographcms.repository.SecretUserRepository;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SecretAuthenticationProvider implements AuthenticationProvider {

    private SecretUserRepository repository;

    public SecretAuthenticationProvider(SecretUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String secret = authentication.getCredentials().toString();
        return repository.loadBySecret(secret).map(user -> {
                 AbstractAuthenticationToken authResult = new SecretToken(List.of(new SimpleGrantedAuthority("ROLE_USER")), secret, user);
                 authResult.setAuthenticated(true);
                 return authResult;
        }).orElseThrow(() -> new BadCredentialsException("Invalid secret"));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SecretToken.class.equals(authentication);
    }
}
