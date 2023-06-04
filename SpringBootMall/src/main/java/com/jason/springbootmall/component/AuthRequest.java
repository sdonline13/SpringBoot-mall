package com.jason.springbootmall.component;

import com.jason.springbootmall.model.UserToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthRequest {
    public boolean authRequestUserById(int userId){
        //得到安全上下文驗證身分 (防止 此token 取得他人資料)
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        int securityContextUserId=((UserToken)authentication.getPrincipal()).getUserId();
        return securityContextUserId ==userId;
    }
}
