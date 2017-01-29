package com.karienomen.service;

import com.karienomen.model.User;

import java.util.List;

/**
 * Created by andreb on 28.01.17.
 */
public interface UserService {

    User findByName(String name);
    User save(User user);
    void delete(User user);
    List<User> findAll();
    List<User> findAll(String search);
    void deleteAll();
}
