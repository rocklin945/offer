package com.rocklin.offer.common.aop;

import com.rocklin.offer.common.annotation.AuthCheck;
import com.rocklin.offer.common.enums.ErrorCode;
import com.rocklin.offer.common.enums.UserRoleEnum;
import com.rocklin.offer.common.exception.BusinessException;
import com.rocklin.offer.model.dto.response.UserLoginResponse;
import com.rocklin.offer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @ClassName AuthCheckInterceptor
 * @Description 鉴权拦截器
 * @Author: rocklin
 */
@Aspect
@Component
@RequiredArgsConstructor
public class AuthCheckInterceptor {

    private final UserService userService;

    @Around("@annotation(checkAuth)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck checkAuth) throws Throwable {
        Integer role = checkAuth.enableRole().getValue();
        UserLoginResponse currentUser = userService.getCurrentUser();
        UserRoleEnum currentUserRole = UserRoleEnum.getEnumByValue(currentUser.getUserRole());
        if (currentUserRole == null) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED);
        }
        if (!currentUserRole.getValue().equals(role)) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED);
        }
        return joinPoint.proceed();
    }
}
