package com.binas.tachographcms.service;

import com.binas.tachographcms.model.entity.Admin;

import java.util.Optional;

public interface AdminService {

    Optional<Admin> getAdmin(String username, String password);

    Optional<Admin> getAdmin();

    void changePassword(String newPassword);
}
