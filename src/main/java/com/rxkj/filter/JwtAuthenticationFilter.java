package com.rxkj.filter;

import com.auth0.jwt.interfaces.Claim;
import com.rxkj.entity.bo.MeiFenUser;
import com.rxkj.util.Commons;
import com.rxkj.util.JwtUtil;
import com.rxkj.util.RedisUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private final RedisUtil redisUtil;

    public JwtAuthenticationFilter(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("MeiFen-token");

        if (Objects.isNull(token)) {
            filterChain.doFilter(request, response);
            return;
        }


        Map<String, Claim> claims = JwtUtil.getClaims(token);

        String userNumbers = claims.get("userNumbers").asString();

        MeiFenUser meiFenUser = redisUtil.getObject(Commons.REDIS_KEY_MEI_FEN_USER + userNumbers, MeiFenUser.class);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(meiFenUser, null, meiFenUser.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);

    }
}
