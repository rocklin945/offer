package com.rocklin.offer.common.aop;

import com.rocklin.offer.common.annotation.MemberCheck;
import com.rocklin.offer.common.enums.ErrorCode;
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
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Field;

import static com.rocklin.offer.common.constants.Constants.*;

/**
 * 会员检查切面
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class MemberCheckAspect {

    private final UserService userService;
    private final JwtUtils jwtUtils;

    @Around("@annotation(memberCheck)")
    public Object doMemberCheck(ProceedingJoinPoint joinPoint, MemberCheck memberCheck) throws Throwable {
        // 获取请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "无法获取请求信息");
        }
        
        HttpServletRequest request = attributes.getRequest();
        
        // 获取当前用户
        String token = request.getHeader(AUTHORIZATION);
        
        // 处理未登录用户
        if (token == null || !token.startsWith(BEARER)) {
            return handleAnonymousUser(request, joinPoint, memberCheck);
        }
        
        // 处理已登录用户
        return handleLoggedInUser(request, joinPoint, memberCheck, token);
    }
    
    /**
     * 处理未登录用户访问
     */
    private Object handleAnonymousUser(HttpServletRequest request, ProceedingJoinPoint joinPoint, MemberCheck memberCheck) throws Throwable {
        String httpMethod = request.getMethod();
        
        if (GET.equalsIgnoreCase(httpMethod)) {
            // GET请求：从URL参数中获取
            checkAnonymousGetRequestParams(request, memberCheck);
        } else if (POST.equalsIgnoreCase(httpMethod)) {
            // POST请求：从请求体DTO中获取
            checkAnonymousPostRequestParams(joinPoint.getArgs(), memberCheck);
        }
        return joinPoint.proceed();
    }
    
    /**
     * 处理已登录用户访问
     */
    private Object handleLoggedInUser(HttpServletRequest request, ProceedingJoinPoint joinPoint, MemberCheck memberCheck, String token) throws Throwable {
        token = token.substring(TOKEN_START_INDEX);
        String userIdStr = jwtUtils.getUserIdFromToken(token);
        if (userIdStr == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        
        Long userId = Long.parseLong(userIdStr);
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        
        // 检查用户角色，管理员(0)和会员(2)可以无限制访问
        if (user.getUserRole() == 0 || user.getUserRole() == 2) {
            return joinPoint.proceed();
        }
        
        // 普通用户(1)需要检查页数和页面大小限制
        String httpMethod = request.getMethod();
        if (GET.equalsIgnoreCase(httpMethod)) {
            // GET请求：从URL参数中获取
            checkGetRequestParams(request, memberCheck);
        } else if (POST.equalsIgnoreCase(httpMethod)) {
            // POST请求：从请求体DTO中获取
            checkPostRequestParams(joinPoint.getArgs(), memberCheck);
        }
        return joinPoint.proceed();
    }
    
    /**
     * 检查未登录用户的GET请求参数
     */
    private void checkAnonymousGetRequestParams(HttpServletRequest request, MemberCheck memberCheck) {
        String pageNumStr = request.getParameter(PAGE_NUM);
        String pageSizeStr = request.getParameter(PAGE_SIZE);
        
        if (pageNumStr != null) {
            try {
                int pageNum = Integer.parseInt(pageNumStr);
                // 未登录用户只能看前2页
                if (pageNum > 2) {
                    throw new BusinessException(ErrorCode.UNAUTHORIZED, "未登录用户只能查看前2页，请登录查看更多职位");
                }
            } catch (NumberFormatException e) {
                log.warn("GET请求页码参数格式错误: {}", pageNumStr);
            }
        }
        
        if (pageSizeStr != null) {
            try {
                int pageSize = Integer.parseInt(pageSizeStr);
                // 未登录用户每页最多10条
                if (pageSize > 10) {
                    throw new BusinessException(ErrorCode.UNAUTHORIZED, "未登录用户每页最多只能查看10条记录，请登录查看更多");
                }
            } catch (NumberFormatException e) {
                log.warn("GET请求页面大小参数格式错误: {}", pageSizeStr);
            }
        }
    }
    
    /**
     * 检查未登录用户的POST请求参数
     */
    private void checkAnonymousPostRequestParams(Object[] args, MemberCheck memberCheck) {
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (arg == null) {
                continue;
            }
            try {
                // 检查 pageNum
                Field pageNumField = findFieldInClassHierarchy(arg.getClass(), PAGE_NUM);
                if (pageNumField != null) {
                    pageNumField.setAccessible(true);
                    Integer pageNum = (Integer) pageNumField.get(arg);

                    if (pageNum != null && pageNum > 2) {
                        throw new BusinessException(ErrorCode.UNAUTHORIZED, "未登录用户只能查看前2页，请登录查看更多职位");
                    }
                }

                // 检查 pageSize
                Field pageSizeField = findFieldInClassHierarchy(arg.getClass(), PAGE_SIZE);
                if (pageSizeField != null) {
                    pageSizeField.setAccessible(true);
                    Integer pageSize = (Integer) pageSizeField.get(arg);
                    if (pageSize != null && pageSize > 10) {
                        throw new BusinessException(ErrorCode.UNAUTHORIZED, "未登录用户每页最多只能查看10条记录，请登录查看更多");
                    }
                }
            } catch (IllegalAccessException e) {
                log.warn("POST请求获取分页参数字段失败: {}", arg.getClass().getName(), e);
            }
        }
    }
    
    /**
     * 检查GET请求参数
     */
    private void checkGetRequestParams(HttpServletRequest request, MemberCheck memberCheck) {
        String pageNumStr = request.getParameter(PAGE_NUM);
        String pageSizeStr = request.getParameter(PAGE_SIZE);
        
        if (pageNumStr != null) {
            try {
                int pageNum = Integer.parseInt(pageNumStr);
                if (pageNum > memberCheck.maxPage()) {
                    throw new BusinessException(ErrorCode.UNAUTHORIZED, memberCheck.message());
                }
            } catch (NumberFormatException e) {
                log.warn("GET请求页码参数格式错误: {}", pageNumStr);
            }
        }
        
        if (pageSizeStr != null) {
            try {
                int pageSize = Integer.parseInt(pageSizeStr);
                if (pageSize > memberCheck.maxPageSize()) {
                    throw new BusinessException(ErrorCode.UNAUTHORIZED, "普通用户每页最多只能查看" + memberCheck.maxPageSize() + "条记录，需要成为会员才能查看更多");
                }
            } catch (NumberFormatException e) {
                log.warn("GET请求页面大小参数格式错误: {}", pageSizeStr);
            }
        }
    }
    
    /**
     * 检查POST请求参数（从DTO对象中获取）
     */
    private void checkPostRequestParams(Object[] args, MemberCheck memberCheck) {
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (arg == null) {
                continue;
            }
            try {
                // 检查 pageNum - 需要在类层次结构中查找字段
                Field pageNumField = findFieldInClassHierarchy(arg.getClass(), PAGE_NUM);
                if (pageNumField != null) {
                    pageNumField.setAccessible(true);
                    Integer pageNum = (Integer) pageNumField.get(arg);

                    if (pageNum != null && pageNum > memberCheck.maxPage()) {
                        throw new BusinessException(ErrorCode.UNAUTHORIZED, memberCheck.message());
                    }
                } else {
                    log.info("参数[{}]在类层次结构中没有找到pageNum字段", i);
                }

                // 检查 pageSize - 需要在类层次结构中查找字段
                Field pageSizeField = findFieldInClassHierarchy(arg.getClass(), PAGE_SIZE);
                if (pageSizeField != null) {
                    pageSizeField.setAccessible(true);
                    Integer pageSize = (Integer) pageSizeField.get(arg);
                    if (pageSize != null && pageSize > memberCheck.maxPageSize()) {
                        log.warn("页面大小超出限制: {} > {}", pageSize, memberCheck.maxPageSize());
                        throw new BusinessException(
                                ErrorCode.UNAUTHORIZED,
                                "普通用户每页最多只能查看 " + memberCheck.maxPageSize()
                                        + " 条记录，需要成为会员才能查看更多"
                        );
                    }
                } else {
                    log.info("参数[{}]在类层次结构中没有找到pageSize字段", i);
                }
            } catch (IllegalAccessException e) {
                log.warn("POST请求获取分页参数字段失败: {}", arg.getClass().getName(), e);
            }
        }
    }
    
    /**
     * 在类层次结构中查找字段（包括父类）
     */
    private Field findFieldInClassHierarchy(Class<?> clazz, String fieldName) {
        Class<?> currentClass = clazz;
        while (currentClass != null) {
            try {
                return currentClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                currentClass = currentClass.getSuperclass();
            }
        }
        return null;
    }
}