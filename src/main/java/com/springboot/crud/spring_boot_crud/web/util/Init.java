package com.springboot.crud.spring_boot_crud.web.util;

import com.springboot.crud.spring_boot_crud.web.model.Role;
import com.springboot.crud.spring_boot_crud.web.model.User;
import com.springboot.crud.spring_boot_crud.web.service.RoleService;
import com.springboot.crud.spring_boot_crud.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Component
public class Init implements CommandLineRunner {

    private UserService userService;

    private RoleService roleService;

    public Init(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) throws Exception {

        Role admin = new Role();
        admin.setName("ROLE_ADMIN");
        roleService.createRole(admin);

        Role user = new Role();
        user.setName("ROLE_USER");
        roleService.createRole(user);

        User userAdmin = new User();
        userAdmin.setName("Ксения");
        userAdmin.setSurname("Болдырева");
        userAdmin.setEmail("ksboldyreva@bk.ru");
        userAdmin.setAge(24);
        userAdmin.setUsername("admin");
        userAdmin.setPassword("admin");
        userService.addUser(userAdmin);
        userAdmin.addRoleToUser(admin);
        userService.updateUser(userAdmin, userAdmin.getId());

        User userUser = new User();
        userUser.setName("Кардай");
        userUser.setSurname("Болдырев");
        userUser.setEmail("karday@mail.ru");
        userUser.setAge(7);
        userUser.setUsername("user");
        userUser.setPassword("user");
        userService.addUser(userUser);
        userUser.addRoleToUser(user);
        userService.updateUser(userUser, userUser.getId());

    }
}
