package com.rootapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rootapp.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    // @Query("")
    public User findByEmail(String email);

}
