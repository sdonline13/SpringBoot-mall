package com.jason.springbootmall.service;

public interface ServiceBase<T,Dto>{
    T getById(Integer id);
    int create(Dto dto);

    void updateById(Integer id,Dto dto);

    void deleteById(Integer id);
}
