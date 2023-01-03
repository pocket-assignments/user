package com.pocket.user.dao;

import com.pocket.user.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<User, String> {
    public User findByUserName(String userName);
}
