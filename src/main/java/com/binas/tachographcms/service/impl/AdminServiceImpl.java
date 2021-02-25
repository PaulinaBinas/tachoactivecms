package com.binas.tachographcms.service.impl;

import com.binas.tachographcms.model.entity.Admin;
import com.binas.tachographcms.repository.AdminRepository;
import com.binas.tachographcms.service.AdminService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    private AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Optional<Admin> getAdmin(String username, String password) {
        return this.adminRepository.getAdminByUsernameAndAndPassword(username, password);
    }

    @Override
    public void changePassword(String newPassword) {
        this.adminRepository.getAdminById(1L).ifPresent(admin -> {
          admin.setPassword(newPassword);
          this.adminRepository.save(admin);
        });
    }

    @Override
    public Optional<Admin> getAdmin() {
        return this.adminRepository.getAdminById(1L);
    }
}
