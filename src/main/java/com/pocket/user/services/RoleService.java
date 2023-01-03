package com.pocket.user.services;

import com.pocket.user.dao.RoleDao;
import com.pocket.user.models.Role;
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
