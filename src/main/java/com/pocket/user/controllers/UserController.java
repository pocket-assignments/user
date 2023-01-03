package com.app.pocket.controllers;

import com.app.pocket.models.User;
import com.app.pocket.pojo.Registration;
import com.app.pocket.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping({"/registerNewUser"})
    public User registerNewUser(@RequestBody Registration registration){

        return userService.registerNewUser(registration);
    }

    @PostMapping({"/assignRole/{userName}/{role}"})
    @PreAuthorize("hasAnyRole('ADMIN')")
    public User assignRole(@PathVariable("userName") String userName,
                           @PathVariable String role){
        return userService.assignRole(userName, role);
    }

    @GetMapping("/forAdmin")
    public String forAdmin(){
        return "For admin";
    }

    @GetMapping("/forProfessor")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    public String forProfessor(){
        return "For professor";
    }

    @GetMapping("/forTA")
    public String forTA(){
        return "For TA";
    }

    @GetMapping("/forStudent")
    public String forStudent(){
        return "For student";
    }
}
