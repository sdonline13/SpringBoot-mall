package com.jason.springbootmall.controller;

import com.jason.springbootmall.dto.UserRegisterRequest;
import com.jason.springbootmall.model.User;
import com.jason.springbootmall.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/users/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest){

        Integer userId= userService.register(userRegisterRequest);
        User user =userService.getUserById(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
