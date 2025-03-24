package com.usermanagement;

import com.usermanagement.application.dto.RoleDTO;
import com.usermanagement.application.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UserManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserManagementApplication.class, args);
    }

    @Bean
    public CommandLineRunner initializeRoles(RoleService roleService) {
        return args -> {
            if (!roleService.existsByName("ROLE_USER")) {
                RoleDTO userRole = new RoleDTO();
                userRole.setName("ROLE_USER");
                userRole.setDescription("Papel padrão para usuários normais");
                roleService.createRole(userRole);
                System.out.println("Papel ROLE_USER criado com sucesso!");
            }

            if (!roleService.existsByName("ROLE_ADMIN")) {
                RoleDTO adminRole = new RoleDTO();
                adminRole.setName("ROLE_ADMIN");
                adminRole.setDescription("Papel para administradores com acesso completo ao sistema");
                roleService.createRole(adminRole);
                System.out.println("Papel ROLE_ADMIN criado com sucesso!");
            }
        };
    }
}