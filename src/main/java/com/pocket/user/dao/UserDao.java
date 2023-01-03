package com.app.pocket.dao;

import com.app.pocket.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<User, String> {
    public User findByUserName(String userName);
}
