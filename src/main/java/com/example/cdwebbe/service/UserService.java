package com.example.cdwebbe.service;

import com.example.cdwebbe.model.User;

import java.util.List;

public interface UserService {
    User login(String userName, String passWord);
    User signUp(User user);
    List<User> getAll();
    String deleteUser(long id);
}
