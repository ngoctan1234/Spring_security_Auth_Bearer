package com.example.demo.services;

import com.example.demo.models.User;

import java.util.List;

public interface IUserService {
    User createUser(User user) throws Exception;
    List<User> getAllUsers();
    User getUserByUsername(String username) throws Exception;
    String login(String username, String password) throws Exception;
}
