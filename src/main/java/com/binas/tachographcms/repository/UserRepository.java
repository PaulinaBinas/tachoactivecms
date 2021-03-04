package com.binas.tachographcms.repository;

import com.binas.tachographcms.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findDistinctById(Integer id);

    Optional<User> findDistinctByCode(String code);

    void deleteUserById(Integer id);

    @Override
    List<User> findAll();

    @Override
    <S extends User> S save(S s);
}
