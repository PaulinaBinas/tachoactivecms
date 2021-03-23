package com.binas.tachographcms.service.impl;

import com.binas.tachographcms.model.entity.User;
import com.binas.tachographcms.model.to.UserTo;
import com.binas.tachographcms.repository.UserRepository;
import com.binas.tachographcms.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends CrudService<User, Integer> implements UserService {

    private UserRepository userRepository;

    private ModelMapper mapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public void addUser(UserTo user) {
        this.userRepository.findAll().stream().forEach(u -> {
            if(user.getPhoneNumber() == null || u.getPhoneNumber().equals(user.getPhoneNumber())) {
                throw new IllegalArgumentException();
            }
        });
        this.userRepository.save(convertToEntity(user));
    }

    @Override
    public void generateNewCode(Integer id) {
        this.userRepository.findDistinctById(id).ifPresentOrElse(user -> {
            String newCode = "";
            do {
                newCode = generateNewCode();
            } while(this.userRepository.findDistinctByCode(newCode).isPresent());
            user.setCode(newCode);
            this.userRepository.save(user);
        },
        () -> {throw new IllegalArgumentException("Wrong id!");});
    }

    @Override
    public List<UserTo> getAllUsers() {
        return this.userRepository.findAll().stream().map(user -> convertToTO(user)).collect(Collectors.toList());
    }

    @Override
    public void removeUserCode(Integer id) {
        this.userRepository.findDistinctById(id).ifPresentOrElse(user -> {
            user.setCode(null);
            this.userRepository.save(user);
        }, () -> {
            throw new IllegalArgumentException("Wrong id!");
        });
    }

    @Override
    public void setUserReminder(Integer days, Integer id) {
        this.userRepository.findDistinctById(id).ifPresentOrElse(user -> {
            user.setDaysReminder(days);
            this.userRepository.save(user);
        }, () -> {
            throw new IllegalArgumentException("Wrong id!");
        });
    }

    @Override
    public UserTo getUserByCode(String code) {
        Optional<User> user = this.userRepository.findDistinctByCode(code);
        if(user.isPresent()) {
            return convertToTO(user.get());
        } else return null;
    }

    private User convertToEntity(UserTo user) {
        return this.mapper.map(user, User.class);
    }

    private UserTo convertToTO(User user) {
        return this.mapper.map(user, UserTo.class);
    }

    private String generateNewCode() {
        Random random = new Random();
        return (random.nextInt(1000) + 1000) * 999 + "";
    }

    @Override
    protected JpaRepository<User, Integer> getRepository() {
        return this.userRepository;
    }

    @Transactional
    @Override
    public void removeUserById(Integer id) {
        this.userRepository.deleteUserById(id);
    }
}
