package com.rocklin.offer.common.utils;

import cn.hutool.core.lang.UUID;
import com.rocklin.offer.common.enums.ErrorCode;
import com.rocklin.offer.common.exception.BusinessException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

import static com.rocklin.offer.common.constants.Constants.LOGIN_TOKEN_KEY_PREFIX;
import static com.rocklin.offer.common.constants.Constants.USERACCOUNT;


/**
 * JWT工具类
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtils {

    @Value("${jwt.secret:mySecretKey}")
    private String secret;

    @Value("${jwt.expiration:86400000}")
    private Long expiration;

    private final StringRedisTemplate redisTemplate;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * 生成JWT token
     */
    public String generateToken(String userId, String userAccount) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(userId)
                .claim(USERACCOUNT, userAccount)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 从token中获取用户ID
     */
    public String getUserIdFromToken(String token) {
        Claims claims;
        try{
            claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            throw new BusinessException(ErrorCode.UNAUTHORIZED, "登录信息失效，请登录");
        }
        return claims.getSubject();
    }

    /**
     * 获取jti
     */
    public String getJtiFromToken(String token) {
        Claims claims;
        try{
            claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            throw new BusinessException(ErrorCode.UNAUTHORIZED, "登录信息失效，请登录");
        }
        return claims.getId();
    }

    // 获取过期秒数
    public long getExpireSeconds() {
        return expiration / 1000;
    }


    /**
     * 验证token是否有效
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty");
        }
        return false;
    }

    public String validateAndGetUserId(String token) {
        String userId = getUserIdFromToken(token);
        String jti = getJtiFromToken(token);

        // Redis 校验
        String redisJti = redisTemplate.opsForValue().get(LOGIN_TOKEN_KEY_PREFIX + userId);
        if (redisJti == null || !redisJti.equals(jti)) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED, "账号已在其他设备登录，请重新登录");
        }

        return userId;
    }

}