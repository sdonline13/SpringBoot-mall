package com.jason.springbootmall.service.ServiceImp;

import com.jason.springbootmall.dao.UserDao;
import com.jason.springbootmall.dto.UserLoginRequest;
import com.jason.springbootmall.dto.UserRegisterRequest;
import com.jason.springbootmall.model.User;
import com.jason.springbootmall.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import java.awt.image.RasterFormatException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class UserServiceImp implements UserService {
    private  final  static Logger log= LoggerFactory.getLogger(UserServiceImp.class);
    @Autowired
    UserDao userDao;

    //Sha256 Hash
    public  String sha256Encode(String password)  {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] hash = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }
    //md5 Hash
    public  String md5Encode(String password){
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }

    @Override
    public int register(UserRegisterRequest userRegisterRequest) {
        userRegisterRequest.setPassword(sha256Encode((userRegisterRequest.getPassword())));
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User getUserById(int userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {
        User user=userDao.getUserByEmail(userLoginRequest.getEmail());
        if(user==null){
            log.warn("信箱: {}  尚未註冊",userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);//尚未註冊
        }
        String hashPassword= sha256Encode( userLoginRequest.getPassword());
        if(!hashPassword.equals(user.getPassword())){
            log.warn("密碼錯誤");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);//密碼錯誤
        }

        return user;//登入成功
    }
}
