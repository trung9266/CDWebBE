package com.example.cdwebbe.service.impl;

import com.example.cdwebbe.model.User;
import com.example.cdwebbe.repository.UserRepository;
import com.example.cdwebbe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public String deleteUser(long id) {
        boolean isExist = userRepository.existsById(id);
        if(isExist){
            userRepository.deleteById(id);
            return "Delete user success";
        }else{
            return "User is not exist";
        }
    }
    @Override
    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.get();
    }
    @Override
    public User save(User u) {
        return userRepository.save(u);
    }
}
