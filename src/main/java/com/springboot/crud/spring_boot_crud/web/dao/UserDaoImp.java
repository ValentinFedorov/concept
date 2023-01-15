package com.springboot.crud.spring_boot_crud.web.dao;


import com.springboot.crud.spring_boot_crud.web.model.Role;
import com.springboot.crud.spring_boot_crud.web.model.User;
import com.springboot.crud.spring_boot_crud.web.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

@Repository
public class UserDaoImp implements UserDao {

    @PersistenceContext
    EntityManager entityManager;

    private PasswordEncoder passwordEncoder;

    private RoleService roleService;

    public UserDaoImp(@Lazy PasswordEncoder passwordEncoder, RoleService roleService) {
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public void addUser(User user) {
        Set<Role> roles = new HashSet<>();
        if (user.getRoles() != null) {
            Iterator<Role> iterator = user.getRoles().iterator();
            while (iterator.hasNext()) {
                Role role = roleService.findRoleByRoleName(iterator.next().getName());
                roles.add(role);
            }
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roles);
        entityManager.persist(user);
    }

    @Override
    public void deleteUser(long id) {
        User user = getUserById(id);
        entityManager.remove(user);
    }

    @Override
    public void updateUser(User user, long id) {
        Set<Role> roles = new HashSet<>();
        if (user.getRoles() != null) {
            Iterator<Role> iterator = user.getRoles().iterator();
            while (iterator.hasNext()) {
                Role role = roleService.findRoleByRoleName(iterator.next().getName());
                roles.add(role);
            }
        }
        User updatedUser = getUserById(id);
        if(!Objects.equals(updatedUser.getPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        user.setRoles(roles);
        entityManager.merge(user);
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public User getUserById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Query query = entityManager.createQuery("select u from User u where u.email = :email")
                .setParameter("email", email);
        User user = (User) query.getSingleResult();
        if(user == null) {
            throw new UsernameNotFoundException(String.format("User with email %s not found", email));
        }
        user.getRoles().size();
        return user;    }
}
