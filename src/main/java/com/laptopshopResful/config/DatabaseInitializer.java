package com.laptopshopResful.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.laptopshopResful.domain.entity.Permission;
import com.laptopshopResful.domain.entity.Role;
import com.laptopshopResful.domain.entity.User;
import com.laptopshopResful.repository.PermissionRepository;
import com.laptopshopResful.repository.RoleRepository;
import com.laptopshopResful.repository.UserRepository;
import com.laptopshopResful.utils.constant.GenderEnum;

@Service
public class DatabaseInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseInitializer(UserRepository userRepository, PermissionRepository permissionRepository,
            RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(">>> Start init database<<<");
        Long cntPermissions = this.permissionRepository.count();
        Long cntRoles = this.roleRepository.count();
        Long cntUsers = this.userRepository.count();

        if (cntPermissions == 0) {
            List<Permission> list = new ArrayList<>();
            list.add(new Permission("Create a permission", "/permissions", "POST", "PERMISSIONS"));
            list.add(new Permission("Update a permission", "/permissions", "PUT", "PERMISSIONS"));
            list.add(new Permission("Delete a permission", "/permissions/{id}", "DELETE", "PERMISSIONS"));
            list.add(new Permission("Fetch permission with id", "permissions/{id}", "GET", "PERMISSIONS"));
            list.add(new Permission("Fetch permission with pagination", "/permissions", "GET", "PERMISSIONS"));

            list.add(new Permission("Create a role", "/roles", "POST", "ROLES"));
            list.add(new Permission("Update a role", "/roles", "PUT", "ROLES"));
            list.add(new Permission("Delete a role", "/roles/{id}", "DELETE", "ROLES"));
            list.add(new Permission("Fetch role with id", "/roles/{id}", "GET", "ROLES"));
            list.add(new Permission("Fetch role with pagination", "/roles", "GET", "ROLES"));

            list.add(new Permission("Create a user", "/users", "POST", "USERS"));
            list.add(new Permission("Update a user", "/users", "PUT", "USERS"));
            list.add(new Permission("Delete a user", "/users/{id}", "DELETE", "USERS"));
            list.add(new Permission("Fetch user with id", "/users/{id}", "GET", "USERS"));
            list.add(new Permission("Fetch user with pagination", "/users", "GET", "USERS"));

            list.add(new Permission("Create a product", "/products", "POST", "PRODUCTS"));
            list.add(new Permission("Update a product", "/products", "PUT", "PRODUCTS"));
            list.add(new Permission("Delete a product", "/products/{id}", "DELETE", "PRODUCTS"));
            list.add(new Permission("Fetch product with id", "/products/{id}", "GET", "PRODUCTS"));
            list.add(new Permission("Fetch product with pagination", "/products", "GET", "PRODUCTS"));

            list.add(new Permission("Create a discount code", "/discounts", "POST", "DISCOUNTS"));
            list.add(new Permission("Update a discount code", "/discounts", "PUT", "DISCOUNTS"));
            list.add(new Permission("Delete a discount code", "/discounts/{id}", "DELETE", "DISCOUNTS"));
            list.add(new Permission("Fetch discount code with id", "/discounts/{id}", "GET", "DISCOUNTS"));
            list.add(new Permission("Fetch discount code with pagination", "/discounts", "GET", "DISCOUNTS"));

            this.permissionRepository.saveAll(list);
        }

        if (cntRoles == 0) {
            List<Permission> allPermissions = this.permissionRepository.findAll();
            Role adminRole = new Role();
            adminRole.setName("SUPER_ADMIN");
            adminRole.setDescription("Admin has full permissions");
            adminRole.setActive(true);
            adminRole.setPermissions(allPermissions);
            this.roleRepository.save(adminRole);
        }
        if (cntUsers == 0) {
            User adminUser = new User();
            adminUser.setEmail("admin@gmail.com");
            adminUser.setAddress("Can Tho");
            adminUser.setAge(18);
            adminUser.setGender(GenderEnum.MALE);
            adminUser.setName("I'm super admin");
            adminUser.setPassword(this.passwordEncoder.encode("123456"));
            Role adminRole = this.roleRepository.findByName("SUPER_ADMIN");
            if (adminRole != null) {
                adminUser.setRole(adminRole);
            }
            this.userRepository.save(adminUser);
        }
        if (cntPermissions != 0 & cntRoles != 0 && cntUsers != 0) {
            System.out.println(">>> SKIP <<<");
        } else {
            System.out.println(">>> END INIT DATABASE");
        }
    }

}
