package com.rocklin.offer.common.interceptor;

import com.rocklin.offer.common.enums.ErrorCode;
import com.rocklin.offer.common.exception.Assert;
import com.rocklin.offer.common.exception.BusinessException;
import com.rocklin.offer.common.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import static com.rocklin.offer.common.constants.Constants.*;

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtils jwtUtils;
    private final StringRedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 如果不是映射到方法，直接放行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 从 Authorization 获取 token
        String authHeader = request.getHeader(AUTHORIZATION);
        Assert.notNull(authHeader, ErrorCode.UNAUTHORIZED, "登录信息失效，请登录");
        Assert.isTrue(authHeader.startsWith(BEARER), ErrorCode.UNAUTHORIZED, "登录信息失效，请登录");

        String token = authHeader.substring(TOKEN_START_INDEX);

        // 验证 token 是否有效
        if (!jwtUtils.validateToken(token)) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED, "登录信息失效，请登录");
        }

        // 获取 userId 和 jti
        String userId = jwtUtils.getUserIdFromToken(token);
        String jti = jwtUtils.getJtiFromToken(token);

        // 校验 Redis 中是否存在对应 jti
        String redisJti = redisTemplate.opsForValue().get(LOGIN_TOKEN_KEY_PREFIX + userId);
        if (redisJti == null || !redisJti.equals(jti)) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED, "账号已在其他设备登录，请重新登录");
        }

        // 验证通过，把 userId 放到 request
        request.setAttribute(USER_ID, userId);

        return true;
    }
}
