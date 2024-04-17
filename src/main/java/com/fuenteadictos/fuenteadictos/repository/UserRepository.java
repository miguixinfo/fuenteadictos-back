package com.fuenteadictos.fuenteadictos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fuenteadictos.fuenteadictos.domain.User;

/**
 * UserRepository
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

    Optional<User> findByUuid(String uuid);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
    
}
