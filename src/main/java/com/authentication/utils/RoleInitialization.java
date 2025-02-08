//package com.authentication.utils;
//
//import com.authentication.model.Role;
//import com.authentication.repository.RoleRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RoleInitialization {
//    @Bean
//    CommandLineRunner initRoles(RoleRepository roleRepository) {
//        return args -> {
//            if (roleRepository.count() == 0) {
//                Role userRole = new Role(null, "ROLE_USER");
//                Role adminRole = new Role(null, "ROLE_ADMIN");
//                roleRepository.save(userRole);
//                roleRepository.save(adminRole);
//            }
//        };
//    }
//}
