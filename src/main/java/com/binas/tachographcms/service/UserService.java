package com.binas.tachographcms.service;

import com.binas.tachographcms.model.to.UserTo;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void addUser(UserTo user);

    void generateNewCode(Integer id);

    List<UserTo> getAllUsers();

    void removeUserCode(Integer id);

    void setUserReminder(Integer days, Integer id);

    UserTo getUserByCode(String code);

    void removeUserById(Integer id);
}
