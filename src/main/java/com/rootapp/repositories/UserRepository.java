package com.rootapp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rootapp.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    // custom query method
    Optional<User> findByEmail(String email);

}
