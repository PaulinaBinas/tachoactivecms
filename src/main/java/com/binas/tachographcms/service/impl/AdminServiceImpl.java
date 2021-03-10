package com.binas.tachographcms.service.impl;

import com.binas.tachographcms.model.entity.Admin;
import com.binas.tachographcms.repository.AdminRepository;
import com.binas.tachographcms.service.AdminService;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    private AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Optional<Admin> getAdmin(String username, String password) {
        return this.adminRepository.getAdminByUsernameAndPassword(username, password);
    }

    @Override
    public void changePassword(String newPassword) {
        this.adminRepository.getFirstByUsername("admin").ifPresentOrElse(admin -> {
          admin.setPassword(newPassword);
          this.adminRepository.save(admin);
        }, () -> {throw new IllegalStateException();});
    }

    @Override
    public Optional<Admin> getAdmin() {
        return this.adminRepository.getAdminById(1L);
    }
}
