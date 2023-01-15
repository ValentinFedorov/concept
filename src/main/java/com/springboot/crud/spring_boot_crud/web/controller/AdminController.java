package com.springboot.crud.spring_boot_crud.web.controller;

import com.springboot.crud.spring_boot_crud.web.model.User;
import com.springboot.crud.spring_boot_crud.web.service.RoleService;
import com.springboot.crud.spring_boot_crud.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private UserService userService;

    private RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String showAllUsers (Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("loggedUser", user);
        model.addAttribute("rolesList", roleService.getListRoles());
        model.addAttribute("newUser", new User());
        return "all-users";
    }

//    @RequestMapping(value = "/addUsers")
//    public String addUsers (@ModelAttribute("user") User user) {
////        User user = new User();
////        List<Role> roles = roleRepository.findAll();
////        model.addAttribute("user", user);
////        model.addAttribute("allRoles", roles);
//        userService.addUser(user);
//        return "addUser";
//    }

    @PostMapping(value = "")
    public String addUser(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

//    @GetMapping("/changeUser/{id}")
//    public String updateUser(@PathVariable("id") long id, Model model) {
//        List<Role> roles = roleRepository.findAll();
//        model.addAttribute("allRoles", roles);
//        model.addAttribute(userService.getUserById(id));
//        return "updateUsers";
//    }

//    @PostMapping("/changeUser/{id}")
//    public String editUser(@PathVariable("id") long id, @ModelAttribute("user") User user) {
//        userService.updateUser(id, user);
//        return "redirect:/admin";
//    }
        @PatchMapping("/changeUser/{id}")
        public String editUser(@PathVariable("id") long id, User user) {
            userService.updateUser(user, id);
            return "redirect:/admin";
}
}
