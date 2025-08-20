package com.rocklin.offer.common.interceptor;


import com.rocklin.offer.common.enums.ErrorCode;
import com.rocklin.offer.common.exception.Assert;
import com.rocklin.offer.common.exception.BusinessException;
import com.rocklin.offer.common.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import static com.rocklin.offer.common.constants.Constants.*;


/**
 * JWT拦截器
 * 用于验证用户请求的token
 */
@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        String token = null;

        //从 Authorization 头获取
        String authHeader = request.getHeader(AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith(BEARER)) {
            token = authHeader.substring(TOKEN_START_INDEX);
        }

        Assert.notNull(token, ErrorCode.UNAUTHORIZED, "登录信息失效，请登录");

        try {
            // 验证token
            if (!jwtUtils.validateToken(token)) {
                throw new BusinessException(ErrorCode.UNAUTHORIZED, "登录信息失效，请登录");
            }

            // 从token中获取用户ID并设置到请求属性中
            String userId = jwtUtils.getUserIdFromToken(token);
            request.setAttribute(USER_ID, userId);

            return true;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED, "登录信息失效，请登录");
        }
    }
}