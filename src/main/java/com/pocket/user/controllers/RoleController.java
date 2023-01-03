package com.pocket.user.controllers;

import com.pocket.user.models.Role;
import com.pocket.user.services.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/users")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public String checkStatus() {
        log.info("Service is up");
        return "Success";
    }

    @PostMapping("/createRole")
    public Role createRole(@RequestBody Role role) {
        return roleService.createRole(role);
    }
    }

