package com.jason.springbootmall.dao;



public interface Dao<T> {
    T getById(Integer id);
}
