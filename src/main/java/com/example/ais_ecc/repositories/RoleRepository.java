package com.example.ais_ecc.repositories;

import com.example.ais_ecc.entity.Role;
import com.example.ais_ecc.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {

    public Optional<Role> findRoleByName(String name);

    boolean existsByName(String role);


}