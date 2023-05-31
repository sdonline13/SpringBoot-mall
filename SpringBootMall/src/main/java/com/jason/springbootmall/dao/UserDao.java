package com.jason.springbootmall.dao;

import com.jason.springbootmall.dto.UserLoginRequest;
import com.jason.springbootmall.dto.UserPasswordUpdateRequest;
import com.jason.springbootmall.dto.UserRegisterRequest;
import com.jason.springbootmall.model.User;
import com.jason.springbootmall.model.UserToken;

public interface UserDao {
    Integer createUser(UserRegisterRequest userRequest);
    User getUserById(int userId);
    User getUserByEmail(String email);

    void updatePassword(UserPasswordUpdateRequest userPasswordUpdateRequest);
    UserToken getTokenByUserId(int userId);
    void createUserToken(int userId,String token);
    void updateUserToken(int userId,String token);
}
