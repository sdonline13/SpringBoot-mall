package com.jason.springbootmall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ResponseResult {
    int code;
    String msg;
    Object body;
}
