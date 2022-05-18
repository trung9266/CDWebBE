package com.example.cdwebbe.service.impl;

import com.example.cdwebbe.model.User;
import com.example.cdwebbe.repository.UserRepository;
import com.example.cdwebbe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public User login(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username,password);
        if(user != null){
            return user;
        }
        return null;
    }

    @Override
    public User signUp(User user) {
        User check = userRepository.findByUsername(user.getUsername());
        if(check == null) {
            User u = userRepository.save(user);
            if (u.getId() != null) {
                return u;
            }
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public String deleteUser(long id) {
        boolean isExist = userRepository.existsById(id);
        if(isExist){
            userRepository.deleteById(id);
            return "Delete user success";
        }else{
            return "User not exist";
        }
    }
}
