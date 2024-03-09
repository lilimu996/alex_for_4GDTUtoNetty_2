package com.rxkj.filter;

import com.alibaba.fastjson.JSON;
import com.rxkj.common.R;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * - 自定义授权异常处理类
 */
public class SimpleAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws ServletException, IOException {
        String json = JSON.toJSONString(
                R.error("please check password")
        );
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
