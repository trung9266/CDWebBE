package com.example.cdwebbe.service;

import com.example.cdwebbe.model.User;

public interface UserService {
    String deleteUser(long id);
    User getUserById(Long id);
    User save(User u);
    User findById(Long id);
    public char[] generatePassword(int length);

    User getUserByEmail(String email);
}
