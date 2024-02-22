package com.rootapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rootapp.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
