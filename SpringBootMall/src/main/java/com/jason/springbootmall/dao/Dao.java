package com.jason.springbootmall.dao;


import java.util.List;

public interface Dao<T,Dto> {
    T getById(Integer id);
    Integer create(Dto dto);
    void  updateById(Integer id, Dto dto);
    void deleteById(Integer id);

    List<T>getAll();
}
