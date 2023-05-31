package com.jason.springbootmall.service.ServiceImp;

import com.jason.springbootmall.dao.UserDao;
import com.jason.springbootmall.dto.ResponseResult;
import com.jason.springbootmall.dto.UserLoginRequest;
import com.jason.springbootmall.dto.UserPasswordUpdateRequest;
import com.jason.springbootmall.dto.UserRegisterRequest;
import com.jason.springbootmall.model.User;
import com.jason.springbootmall.model.UserToken;
import com.jason.springbootmall.service.UserService;
import com.jason.springbootmall.spcurity.UserDetailsImp;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import utils.JwtUtil;

import java.awt.image.RasterFormatException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class UserServiceImp implements UserService {
    private  final  static Logger log= LoggerFactory.getLogger(UserServiceImp.class);
    @Autowired
    UserDao userDao;

    @Autowired
    AuthenticationManager authenticationManager;

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
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        String encodePassword=bCryptPasswordEncoder.encode(userRegisterRequest.getPassword());
        userRegisterRequest.setPassword(encodePassword);
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
    public ResponseResult login(UserLoginRequest userLoginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken =new UsernamePasswordAuthenticationToken(userLoginRequest.getEmail(),userLoginRequest.getPassword());
        Authentication authentication= authenticationManager.authenticate(authenticationToken);//會自動調用 ProvideManager 然後調用 UserDetailsService進行驗證
        if(Objects.isNull(authentication))//傳回空值代表認證失敗
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        //通過 使用userId生成 jwt ，返回jwt
        UserDetailsImp userDetail = (UserDetailsImp) authentication.getPrincipal();
        String userId =String.valueOf( userDetail.getUser().getUserId());
        //
        //判斷資料庫內有無token
        UserToken userToken = userDao.getTokenByUserId(Integer.parseInt(userId) );
        if (!Objects.isNull(userToken)){
            Map<String,String> map=new HashMap<>();
            map.put("token",userToken.getToken());
            return new ResponseResult(200,"登入成功",map);
        }


        JwtUtil jwtUtil = new JwtUtil();

        //判斷是否過期
        if(userToken!=null &&jwtUtil.validateToken(userToken.getToken())==null){
            String jwt = jwtUtil.createJwt(userId);
            Map<String,String> map=new HashMap<>();
            map.put("token",jwt);
            userDao.updateUserToken(Integer.parseInt(userId) ,jwt);
            return new ResponseResult(200,"登入成功",map);
        }
        //生成token 存入 db
        String jwt = jwtUtil.createJwt(userId);

        Map<String,String> map=new HashMap<>();
        map.put("token",jwt);
        userDao.createUserToken(Integer.parseInt(userId) ,jwt);
        return new ResponseResult(200,"登入成功",map);
    }

    @Override
    public void updatePassword(UserPasswordUpdateRequest userPasswordUpdateRequest) {
        User user=userDao.getUserByEmail(userPasswordUpdateRequest.getEmail());
        if(user == null){
            log.warn("信箱: {}  尚未註冊",userPasswordUpdateRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);//尚未註冊
        }
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        String encodePassword=bCryptPasswordEncoder.encode(userPasswordUpdateRequest.getNewPassword());
        userPasswordUpdateRequest.setNewPassword(encodePassword);
        userDao.updatePassword(userPasswordUpdateRequest);
    }
}
