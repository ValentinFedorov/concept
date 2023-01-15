package com.springboot.crud.spring_boot_crud.web.dao;



import com.springboot.crud.spring_boot_crud.web.model.Role;
import com.springboot.crud.spring_boot_crud.web.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserDao extends UserDetailsService {

    void addUser(User user);

    void deleteUser(long id);

    void updateUser(User user, long id);

    List<User> getAllUsers();

    User getUserById(long id);

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
