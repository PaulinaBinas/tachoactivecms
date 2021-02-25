package com.binas.tachographcms.repository;

import com.binas.tachographcms.model.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {

    Optional<Email> getEmailById(Long id);

    @Override
    <S extends Email> S save(S s);
}
