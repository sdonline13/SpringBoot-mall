package com.jason.springbootmall.dao.impl;

import com.jason.springbootmall.dao.UserDao;
import com.jason.springbootmall.dao.rowMapper.UserRowMapper;
import com.jason.springbootmall.dto.UserLoginRequest;
import com.jason.springbootmall.dto.UserRegisterRequest;
import com.jason.springbootmall.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDaoImp implements UserDao {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createUser(UserRegisterRequest userRequest) {
        String sql = "INSERT INTO `user`(email,password,create_date,last_modified_date) VALUES (:email, :password, :createDate,:lastModifiedDate)";
        Map<String,Object> map=new HashMap<>();
        map.put("email",userRequest.getEmail());
        map.put("password",userRequest.getPassword());
        Date now = new Date();
        map.put("createDate",now);
        map.put("lastModifiedDate",now);
        KeyHolder keyHolder=new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);
        int userId=keyHolder.getKey().intValue();
        return userId;
    }

    @Override
    public User getUserById(int userId) {
        String sql ="Select user_Id ,email ,password ,create_date ,last_modified_date from `user` where user_Id = :userId";
        Map<String,Object> map=new HashMap<>();
        map.put("userId",userId);
        List<User> rsList =namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());
        if(rsList != null && rsList.size() > 0)
            return rsList.get(0);
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        String sql ="Select user_Id ,email ,password ,create_date ,last_modified_date from `user` where email = :email";
        Map<String,Object> map=new HashMap<>();
        map.put("email",email);
        List<User> rsList =namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());
        if(rsList != null && rsList.size() > 0)
            return rsList.get(0);
        return null;
    }


}
