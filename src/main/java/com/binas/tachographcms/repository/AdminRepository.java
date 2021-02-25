package com.binas.tachographcms.repository;

import com.binas.tachographcms.model.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> getAdminByUsernameAndAndPassword(String username, String password);

    Optional<Admin> getAdminById(Long id);

    @Override
    <S extends Admin> S save(S s);
}
