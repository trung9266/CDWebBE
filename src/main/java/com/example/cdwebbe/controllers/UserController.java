package com.example.cdwebbe.controllers;

import com.example.cdwebbe.dto.UserDTO;
import com.example.cdwebbe.model.User;
import com.example.cdwebbe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.cdwebbe.model.ResponseObject;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public UserDTO Login(@RequestBody User user){
        User u = userService.login(user.getUsername(),user.getPassword());
        if(u!=null){
            UserDTO dto = new UserDTO();
            dto.setId(u.getId());
            dto.setFullName(u.getFullName());
            dto.setEmail(u.getEmail());
            dto.setRole(u.getRole());
            return dto;
        }
        return null;
    }

    @PostMapping("/signup")
    public User SignUp(@RequestBody User user){
        return userService.signUp(user);
    }

    @GetMapping("/getAll")
    public List<User> getAll(){
        return userService.getAll();
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<ResponseObject> deleteUser(
            @PathVariable(value = "id") long id
    ){
        String message = userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        "ok",
                        message,
                        ""+id
                )
        );
    }
}
