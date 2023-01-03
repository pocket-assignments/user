package com.pocket.user.services;

import com.pocket.user.dao.RoleDao;
import com.pocket.user.dao.UserDao;
import com.pocket.user.models.Role;
import com.pocket.user.models.User;
import com.pocket.user.pojo.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerNewUser(Registration registration){

        User user = new User();
        user.setUserName(registration.getUserName());

        user.setPassword(getEncodedPassword(registration.getUserPassword()));
        user.setLastName(registration.getLastName());
        user.setFirstName(registration.getFirstName());

        Role studentRole = new Role();
        studentRole.setRoleName("STUDENT");
        studentRole.setRoleDesc("Student privileges");

        Set<Role> roles = new HashSet();
        roles.add(studentRole);

        user.setRoles(roles);
        return userDao.save(user);
    }

    private String getEncodedPassword(String password){
        System.out.println("Inside UserService.getEncodedPassword");
        return passwordEncoder.encode(password);
    }

    public User assignRole(String userName, String role){
       User user = userDao.findByUserName(userName);
       Role roleObj = roleDao.findByRoleName(role).orElse(new Role());

        Set<Role> roles = user.getRoles();
        roles.add(roleObj);

        return userDao.save(user);
    }
}


