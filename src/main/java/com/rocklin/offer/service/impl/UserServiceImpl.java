package com.rocklin.offer.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.rocklin.offer.common.enums.ErrorCode;
import com.rocklin.offer.common.enums.UserRoleEnum;
import com.rocklin.offer.common.exception.Assert;
import com.rocklin.offer.common.response.PageResponse;
import com.rocklin.offer.common.utils.AvatarUtil;
import com.rocklin.offer.common.utils.EncryptPasswordUtil;
import com.rocklin.offer.common.utils.IpUtil;
import com.rocklin.offer.common.utils.JwtUtils;
import com.rocklin.offer.mapper.UserMapper;
import com.rocklin.offer.mapper.WebInfoMapper;
import com.rocklin.offer.model.dto.request.UserLoginRequest;
import com.rocklin.offer.model.dto.request.UserPageQueryRequest;
import com.rocklin.offer.model.dto.request.UserRegisterRequest;
import com.rocklin.offer.model.dto.request.UserUpdateRequest;
import com.rocklin.offer.model.dto.response.UserLoginResponse;
import com.rocklin.offer.model.entity.User;
import com.rocklin.offer.model.entity.WebInfo;
import com.rocklin.offer.service.InviteCommissionService;
import com.rocklin.offer.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.rocklin.offer.common.constants.Constants.*;
import static org.springframework.web.context.request.RequestAttributes.REFERENCE_REQUEST;

/**
 * @ClassName UserServiceImpl
 * @Description 用户服务实现类
 * @Author: rockli
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final JwtUtils jwtUtils;
    private final EncryptPasswordUtil encryptPasswordUtil;
    private final WebInfoMapper webInfoMapper;
    private final InviteCommissionService commissionService;

    @Override
    public Long register(UserRegisterRequest req, HttpServletRequest httpServletRequest) {
        // 查询用户是否已存在
        User userQuery = userMapper.query(req.getUserAccount());
        Assert.isNull(userQuery, ErrorCode.OPERATION_ERROR, "用户已存在");
        // 插入
        User user = new User();
        user.setUserAccount(req.getUserAccount());
        // 加密密码
        user.setUserPassword(encryptPasswordUtil.getEncryptPassword(req.getUserPassword()));
        user.setUserName(USER_PREFIX + RandomUtil.randomString(6));

        //赠送3天会员
        user.setUserRole(UserRoleEnum.VIP.getValue());
        user.setMemberExpireTime(LocalDateTime.now().plusDays(THREE));

        user.setUserAvatar(AvatarUtil.generateRandomAvatarUrl(req.getUserAccount()));
        user.setUserProfile("这个人很懒，什么都没有留下。");
        // 设置邀请码
        user.setInviterCode(req.getInviterCode());
        Long res = userMapper.insert(user);
        Assert.isTrue(res > 0, ErrorCode.OPERATION_ERROR, "数据库异常，注册失败");
        
        // 处理邀请逻辑
        if (req.getInviterCode() != null && !req.getInviterCode().isEmpty()) {
            try {
                User inviter = userMapper.selectByInviteCode(USER_PREFIX + req.getInviterCode());
                if (inviter != null) {
                    commissionService.handleUserInvitation(inviter.getId(), user.getId());
                }
            } catch (Exception e) {
                log.warn("处理邀请逻辑时发生异常: {}", e.getMessage());
            }
        }
        
        //更新最新活动
        WebInfo webInfo = getWebInfo();
        webInfo.setActivity1("新用户注册：" + user.getUserName());
        webInfoMapper.updateWebInfo(webInfo);
        log.info("用户注册成功：{} ,ip: {}", user.getUserName(), IpUtil.getIp(httpServletRequest));
        return res;
    }

    @Override
    public UserLoginResponse login(UserLoginRequest req, HttpServletRequest httpServletRequest) {
        User user = new User();
        user.setUserAccount(req.getUserAccount());
        user.setUserPassword(encryptPasswordUtil.getEncryptPassword(req.getUserPassword()));
        User queryUser = userMapper.queryByPassword(user);
        Assert.notNull(queryUser, ErrorCode.OPERATION_ERROR, "用户不存在或密码错误");

        memberIsExpire(queryUser.getId(), queryUser);

        // 生成JWT token
        String token = jwtUtils.generateToken(queryUser.getId().toString(), queryUser.getUserAccount());

        // 构建响应对象
        UserLoginResponse response = buildUserResponse(queryUser);
        response.setToken(token);

        //更新最新活动
        WebInfo webInfo = getWebInfo();
        webInfo.setActivity1("用户登录：" + queryUser.getUserName());
        webInfoMapper.updateWebInfo(webInfo);
        log.info("用户登录成功：{} ,ip: {}", queryUser.getUserName(), IpUtil.getIp(httpServletRequest));
        return response;
    }

    private WebInfo getWebInfo() {
        WebInfo webInfo = webInfoMapper.selectWebInfo();
        webInfo.setActivity5(webInfo.getActivity4());
        webInfo.setActivity5Time(webInfo.getActivity4Time());
        webInfo.setActivity4(webInfo.getActivity3());
        webInfo.setActivity4Time(webInfo.getActivity3Time());
        webInfo.setActivity3(webInfo.getActivity2());
        webInfo.setActivity3Time(webInfo.getActivity2Time());
        webInfo.setActivity2(webInfo.getActivity1());
        webInfo.setActivity2Time(webInfo.getActivity1Time());
        webInfo.setActivity1Time(LocalDateTime.now());
        return webInfo;
    }

    @Override
    public UserLoginResponse getCurrentUser(Long userId) {
        User user = userMapper.selectById(userId);
        Assert.notNull(user, ErrorCode.OPERATION_ERROR, "用户不存在");
        memberIsExpire(userId, user);
        // 构建响应对象（不返回token，因为获取当前用户时不需要重新生成token）
        UserLoginResponse response = buildUserResponse(user);
        return response;
    }

    private void memberIsExpire(Long userId, User user) {
        LocalDateTime memberExpireTime = user.getMemberExpireTime();
        if (memberExpireTime != null && user.getUserRole()==2
                && memberExpireTime.isBefore(LocalDateTime.now())) {
            UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
            userUpdateRequest.setId(userId);
            userUpdateRequest.setUserRole(UserRoleEnum.USER.getValue());
            userUpdateRequest.setMemberExpireTime(null);
            userMapper.updateById(userUpdateRequest);
        }
    }

    @Override
    public UserLoginResponse getCurrentUser() {
        // 从当前请求中获取用户ID
        String userId = getUserIdFromRequest();
        Assert.notNull(userId, ErrorCode.NOT_LOGIN_ERROR, "用户未登录");
        // 调用已有的方法获取用户信息
        return getCurrentUser(Long.valueOf(userId));
    }

    /**
     * 从当前请求中获取用户ID
     */
    @Override
    public String getUserIdFromRequest() {
        // 使用RequestContextHolder获取当前请求
        HttpServletRequest request = ((HttpServletRequest) RequestContextHolder
                .getRequestAttributes().resolveReference(REFERENCE_REQUEST));
        // 从请求属性中获取用户ID
        return (String) request.getAttribute(USER_ID);
    }

    @Override
    public void logout(Long userId) {
        // 在JWT无状态架构中，登出主要是客户端清除token
        // 服务端可以记录登出日志或进行其他清理操作
        // 这里验证用户存在即可，前端收到请求后需要清除localStorage中的token
        User user = userMapper.selectById(userId);
        Assert.notNull(user, ErrorCode.OPERATION_ERROR, "用户不存在");
    }

    @Override
    public User getUserById(Long id) {
        User user = userMapper.selectById(id);
        Assert.notNull(user, ErrorCode.OPERATION_ERROR, "用户不存在");
        return user;
    }

    @Override
    public void deleteUser(Long id) {
        Long result = userMapper.deleteById(id);
        Assert.isTrue(result > 0, ErrorCode.OPERATION_ERROR, "数据库异常，用户删除失败");
    }

    @Override
    public void updateUser(UserUpdateRequest req) {
        if (req.getUserPassword() != null) {
            req.setUserPassword(encryptPasswordUtil.getEncryptPassword(req.getUserPassword()));
        }
        if(req.getUserRole() == UserRoleEnum.VIP.getValue()){
            commissionService.handleUserBecomeMember(req.getId());
        }
        Long result = userMapper.updateById(req);
        Assert.isTrue(result > 0, ErrorCode.OPERATION_ERROR, "数据库异常，用户更新失败");
    }

    private UserLoginResponse buildUserResponse(User user) {
        UserLoginResponse response = new UserLoginResponse();
        response.setUserId(user.getId());
        response.setUserAccount(user.getUserAccount());
        response.setUserName(user.getUserName());
        response.setUserAvatar(user.getUserAvatar());
        response.setUserProfile(user.getUserProfile());
        response.setUserRole(user.getUserRole());
        response.setMemberExpireTime(user.getMemberExpireTime());
        response.setCreateTime(user.getCreateTime());
        response.setUpdateTime(user.getUpdateTime());
        return response;
    }

    @Override
    public PageResponse<UserLoginResponse> listUserByPageWithFilter(UserPageQueryRequest request) {
        // 计算偏移量
        int offset = (request.getPageNum() - 1) * request.getPageSize();
        // 查询总记录数（带过滤条件）
        long total = userMapper.countTotal(request);
        // 查询用户列表（带过滤条件）
        List<User> userList = userMapper.selectListWithLimit(request, offset, request.getPageSize());
        // 转换为resp
        List<UserLoginResponse> userLoginList = userList.stream()
                .map(this::buildUserResponse)
                .collect(Collectors.toList());

        return new PageResponse<>(userLoginList, total, request.getPageNum(), request.getPageSize());
    }
}
