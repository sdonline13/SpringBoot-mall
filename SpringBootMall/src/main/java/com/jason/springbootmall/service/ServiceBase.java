package com.jason.springbootmall.service;

public interface ServiceBase<T,Dto>{
    T getById(Integer id);
    int create(Dto dto);
}
