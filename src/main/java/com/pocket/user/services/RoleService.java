package com.app.pocket.services;

import com.app.pocket.dao.RoleDao;
import com.app.pocket.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    public Role createRole(Role role){
        return roleDao.save(role);
    }
}
