package com.jason.springbootmall.service;

import com.jason.springbootmall.dto.UserLoginRequest;
import com.jason.springbootmall.dto.UserRegisterRequest;
import com.jason.springbootmall.model.User;

public interface UserService {
   int register(UserRegisterRequest userRegisterRequest);
   User getUserById(int userId);
   User getUserByEmail(String email);

   User login(UserLoginRequest userLoginRequest);
}
