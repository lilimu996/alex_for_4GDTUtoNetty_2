package com.rxkj.filter;

import com.alibaba.fastjson.JSONObject;
import com.rxkj.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    R exceptionHandle(Exception e) {
        e.printStackTrace();
        return R.error("发生异常，请联系开发者");
    }
}
