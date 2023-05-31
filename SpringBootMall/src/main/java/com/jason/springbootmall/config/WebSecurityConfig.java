package com.jason.springbootmall.config;

import com.jason.springbootmall.spcurity.filter.JWTFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration

public class WebSecurityConfig{

    @Autowired
    JWTFilter jwtFilter;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //在HttpSecurity中註意使用了lambda寫法，使用這種寫法之後，每個設置都直接返回HttpSecurity對象
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // 禁用basic明文验证
                .httpBasic().disable()
                // 前后端分离架构不需要csrf保护
                .csrf().disable()
                // 禁用默认登出页
                .logout().disable()
                // 前后端分离是无状态的，不需要session了，直接禁用。
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        // 允许所有OPTIONS请求
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        // 允许直接访问授权登录接口
                        .requestMatchers( "/products/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/users/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/products").permitAll()
                        // 允许 SpringMVC 的默认错误地址匿名访问
                        .requestMatchers("/error").permitAll()
                        // 其他所有接口必须有Authority信息，Authority在登录成功后的UserDetailsImpl对象中默认设置“ROLE_USER”
                        //.requestMatchers("/**").hasAnyAuthority("ROLE_USER")
                        // 允许任意请求被已登录用户访问，不检查Authority
                        .anyRequest().authenticated());

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);//設定FILTER 出現在哪個CLASS 之後
        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
