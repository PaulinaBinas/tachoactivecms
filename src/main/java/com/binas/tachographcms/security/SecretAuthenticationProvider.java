package com.binas.tachographcms.security;

import com.binas.tachographcms.model.entity.User;
import com.binas.tachographcms.model.to.UserTo;
import com.binas.tachographcms.repository.SecretUserRepository;
import com.binas.tachographcms.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SecretAuthenticationProvider implements AuthenticationProvider {

    private UserService userService;

    private ModelMapper mapper;

    public SecretAuthenticationProvider(UserService userService, ModelMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String secret = authentication.getCredentials().toString();
        UserTo userto = userService.getUserByCode(secret);
        return Optional.of(userService.getUserByCode(secret)).map(user -> {
                 AbstractAuthenticationToken authResult = new SecretToken(List.of(new SimpleGrantedAuthority("ROLE_USER")), secret, this.mapper.map(user, User.class));
                 authResult.setAuthenticated(true);
                 return authResult;
        }).orElseThrow(() -> new BadCredentialsException("Invalid secret"));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SecretToken.class.equals(authentication);
    }
}
