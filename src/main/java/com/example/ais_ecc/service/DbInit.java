package com.example.ais_ecc.service;

import com.example.ais_ecc.entity.Role;
import com.example.ais_ecc.entity.User;
import com.example.ais_ecc.repositories.RoleRepository;
import com.example.ais_ecc.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class DbInit {

    @Value("${spring.jpa.hibernate.ddl-auto}")
    String createOrdesrs;
    UserRepository userRepository;
    RoleRepository roleRepository;



    BCryptPasswordEncoder passwordEncoder;

    public DbInit(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;

        passwordEncoder = new BCryptPasswordEncoder();
    }


    public void Init() throws Exception {

        try {
            if (createOrdesrs.equals("create")) {

                CreateRoles();
//                CreateUsers();

            }
        } catch (Exception exc) {
            System.out.println(exc.getMessage());
            throw new Exception("Ошибка при автозаполнении базы данных");
        }

    }

    private void CreateRoles() throws Exception {


        var roles = new ArrayList<String>();
        roles.add("ROLE_ADMIN"); // Admin
        roles.add("ROLE_USER"); // User

        for (var role : roles)
            if (!roleRepository.existsByName(role))
                roleRepository.save(new Role(role));
    }








}
