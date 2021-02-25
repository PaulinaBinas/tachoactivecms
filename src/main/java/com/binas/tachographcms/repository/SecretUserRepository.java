package com.binas.tachographcms.repository;

import com.binas.tachographcms.model.entity.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SecretUserRepository {

    private UserRepository userRepository;

    public SecretUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> loadBySecret(String secret) {
        return userRepository.findDistinctByCode(secret);
    }
}
