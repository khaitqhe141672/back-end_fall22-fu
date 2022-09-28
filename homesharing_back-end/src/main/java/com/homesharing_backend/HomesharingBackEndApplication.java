package com.homesharing_backend;

import com.homesharing_backend.data.entity.ERole;
import com.homesharing_backend.data.entity.Role;
import com.homesharing_backend.data.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class HomesharingBackEndApplication implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(HomesharingBackEndApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(ERole.ROLE_CUSTOMER));
        roles.add(new Role(ERole.ROLE_ADMIN));
        roles.add(new Role(ERole.ROLE_HOST));
        roles.forEach(role -> {
            if (!roleRepository.existsByName(role.getName())) {
                roleRepository.save(role);
            }
        });
    }

}
