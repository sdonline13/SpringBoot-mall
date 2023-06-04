package com.jason.springbootmall.spcurity;

import com.jason.springbootmall.dao.UserDao;
import com.jason.springbootmall.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserDetailsServiceImp  implements UserDetailsService {
    @Autowired
    UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =userDao.getUserByEmail(username);

        //查詢對應權限
        List<String> permissionsList=new ArrayList<>(Arrays.asList("user","manager"));
        return new UserDetailsImp(user,permissionsList);
    }

}
