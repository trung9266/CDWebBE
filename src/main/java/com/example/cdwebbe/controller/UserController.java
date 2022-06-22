package com.example.cdwebbe.controller;

import com.example.cdwebbe.model.User;
import com.example.cdwebbe.model.UserInfo;
import com.example.cdwebbe.repository.UserRepository;
import com.example.cdwebbe.security.CurrentUser;
import com.example.cdwebbe.security.UserPrincipal;
import com.example.cdwebbe.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.example.cdwebbe.security.CurrentUser;
import com.example.cdwebbe.security.CustomUserDetailsService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/me")
    public UserInfo getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        System.out.println(currentUser.getId());
        UserInfo userSummary = new UserInfo(currentUser.getId(), currentUser.getUsername(), currentUser.getName(),currentUser.getEmail(), currentUser.getGender(), currentUser.getAddress(), currentUser.getPhone(), currentUser.getRoles());
        return userSummary;
    }
    @GetMapping("getalluser")
    public ResponseEntity getAllUser(){
        List<User> result = userRepository.findAll();
        return ResponseEntity.ok(result);
    }
    @PutMapping("/update/{id}")
    public User updateUser(@PathVariable(value = "id") Long id, @RequestBody User u){
        User user = userService.getUserById(id);
        if(user!=null){
            user.setName(u.getName());
            if (userRepository.findByEmail(u.getEmail()).isPresent()){

            }else{
                user.setEmail(u.getEmail());
            }

            user.setGender(u.getGender());
            user.setAddress(u.getAddress());
            user.setPhone(u.getPhone());
            return userService.save(user);
        }
        return null;
    }

}
