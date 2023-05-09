package com.jason.springbootmall.service.ServiceImp;

import com.jason.springbootmall.dao.UserDao;
import com.jason.springbootmall.dto.UserRegisterRequest;
import com.jason.springbootmall.model.User;
import com.jason.springbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    UserDao userDao;

    //Sha256 Hash
    public static String encode(String password)  {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] hash = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }

    @Override
    public int register(UserRegisterRequest userRegisterRequest) {
        userRegisterRequest.setPassword(encode((userRegisterRequest.getPassword())));
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User getUserById(int userId) {
        return userDao.getUserById(userId);
    }
}
