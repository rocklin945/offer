package com.rocklin.offer.common.aop;

import com.rocklin.offer.common.annotation.PageAccessLimit;
import com.rocklin.offer.common.enums.ErrorCode;
import com.rocklin.offer.common.enums.UserRoleEnum;
import com.rocklin.offer.common.exception.BusinessException;
import com.rocklin.offer.common.utils.JwtUtils;
import com.rocklin.offer.model.entity.User;
import com.rocklin.offer.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.rocklin.offer.common.constants.Constants.*;

/**
 * 分页访问限制切面
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class PageAccessLimitAspect {

    private final UserService userService;
    private final JwtUtils jwtUtils;

    @Around("@annotation(pageAccessLimit)")
    public Object checkPageAccess(ProceedingJoinPoint joinPoint, PageAccessLimit pageAccessLimit) throws Throwable {
        // 获取请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "无法获取请求信息");
        }
        HttpServletRequest request = attributes.getRequest();

        // 获取分页参数
        Integer pageNum = extractPageNumFromPath(request);
        if (pageNum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数错误");
        }

        // 获取 token
        String token = request.getHeader(AUTHORIZATION);

        // 未登录用户
        if (token == null || !token.startsWith(BEARER)) {
            if (pageNum > pageAccessLimit.maxPageForAnonymous()) {
                throw new BusinessException(ErrorCode.UNAUTHORIZED,"未登录用户最多只能查看" + pageAccessLimit.maxPageForAnonymous() + "条记录，登录查看更多");
            }
            return joinPoint.proceed();
        }

        // 已登录用户
        token = token.substring(TOKEN_START_INDEX);
        String userIdStr = jwtUtils.validateAndGetUserId(token);
        if (userIdStr == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        Long userId = Long.parseLong(userIdStr);
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        // 管理员 / 会员无限制
        if (UserRoleEnum.ADMIN.getValue().equals(user.getUserRole())
                || UserRoleEnum.VIP.getValue().equals(user.getUserRole())) {
            return joinPoint.proceed();
        }

        // 普通用户受限制
        if (UserRoleEnum.USER.getValue().equals(user.getUserRole())
                && pageNum > pageAccessLimit.maxPageForUser()) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED,"普通用户最多查看" + pageAccessLimit.maxPageForUser() + "条记录，成为会员查看更多");
        }

        return joinPoint.proceed();
    }

    private Integer extractPageNumFromPath(HttpServletRequest request) {
        String uri = request.getRequestURI(); // /api/pdf/preview/uuid/page/1
        // 匹配路径里的 /page/{num}
        Pattern pattern = Pattern.compile(".*/page/(\\d+)$");
        Matcher matcher = pattern.matcher(uri);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return null;
    }

    /**
     * 从 GET 参数里提取 pageNum
     */
    private Integer extractPageNum(HttpServletRequest request) {
        String pageNumStr = request.getParameter(PAGE_NUM);
        if (pageNumStr != null) {
            try {
                return Integer.parseInt(pageNumStr);
            } catch (NumberFormatException e) {
                log.warn("GET请求页码参数格式错误: {}", pageNumStr);
            }
        }
        return null;
    }
}
