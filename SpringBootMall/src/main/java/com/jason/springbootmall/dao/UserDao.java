package com.jason.springbootmall.dao;

import com.jason.springbootmall.dto.UserLoginRequest;
import com.jason.springbootmall.dto.UserPasswordUpdateRequest;
import com.jason.springbootmall.dto.UserRegisterRequest;
import com.jason.springbootmall.model.User;

public interface UserDao {
    Integer createUser(UserRegisterRequest userRequest);
    User getUserById(int userId);
    User getUserByEmail(String email);

    void updatePassword(UserPasswordUpdateRequest userPasswordUpdateRequest);
}
