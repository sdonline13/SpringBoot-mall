package com.jason.springbootmall.dao;



public interface Dao<T,Dto> {
    T getById(Integer id);
    Integer create(Dto dto);
    void  updateById(Integer id, Dto dto);
}
