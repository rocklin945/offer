# 数据库初始化

-- 创建库
create database if not exists offer;

-- 切换库
use offer;

-- 用户表
-- 以下是建表语句

-- 用户表
create table if not exists user
(
    id           bigint                                 comment 'id' primary key,
    user_account  varchar(256)                           not null comment '账号',
    user_password varchar(512)                           not null comment '密码',
    user_name     varchar(256)                           null comment '用户昵称',
    user_avatar   varchar(1024)                          null comment '用户头像',
    user_profile  varchar(512)                           null comment '用户简介',
    user_role     int          default 1                 not null comment '用户角色：0:管理员 1:普通用户 2:会员',
    member_expire_time datetime                             null comment '会员到期时间',
    inviter_code       varchar(64)                        null comment '注册时使用的邀请码',
    create_time   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete     tinyint      default 0                 not null comment '是否删除',
    UNIQUE KEY uk_user_account (user_account),
    INDEX idx_user_name (user_name)
) comment '用户' collate = utf8mb4_unicode_ci;

-- 招聘信息表
CREATE TABLE if not exists job_info (
     id                 BIGINT      PRIMARY KEY COMMENT '主键ID',
     company_name       VARCHAR(255) NOT NULL COMMENT '公司名称',
     company_type       VARCHAR(100) COMMENT '公司类型',
     industry           VARCHAR(100) COMMENT '所属行业',

     recruit_type       VARCHAR(50) COMMENT '招聘类型(校招/社招/实习)',
     work_location      VARCHAR(255) COMMENT '工作地点',
     recruit_target     VARCHAR(100) COMMENT '招聘对象(20xx年毕业)',

     position_name      VARCHAR(2048) COMMENT '岗位(专业/岗位名称)',

     start_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
     deadline           VARCHAR(100) COMMENT '投递截止(尽快投递/x.x截止)',

     related_link       VARCHAR(2048) COMMENT '相关链接',
     announcement       TEXT COMMENT '招聘公告',
     referral_code      VARCHAR(100) COMMENT '内推码',
     remark             TEXT COMMENT '备注'
) COMMENT='招聘投递信息表' collate = utf8mb4_unicode_ci;

-- 用户投递记录表
CREATE TABLE IF NOT EXISTS user_job_apply
(
    id             BIGINT PRIMARY KEY COMMENT '主键ID',
    user_id        BIGINT NOT NULL COMMENT '用户ID',
    job_id         BIGINT NOT NULL COMMENT '招聘信息ID',
    application_status VARCHAR(100) COMMENT '投递进度',
    personal_note     VARCHAR(2048) COMMENT '个人备注',
    create_time    DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    update_time    DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_delete      TINYINT  DEFAULT 0 NOT NULL COMMENT '是否删除',

    UNIQUE KEY uk_user_job (user_id, job_id),   -- 保证同一用户对同一岗位只投递一次
    INDEX idx_user_id (user_id),
    INDEX idx_job_id (job_id),
    INDEX idx_application_status (application_status)
) COMMENT='用户投递记录表' COLLATE = utf8mb4_unicode_ci;

CREATE TABLE web_info (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          pv BIGINT DEFAULT 0 COMMENT '网站总浏览量',
                          image_url VARCHAR(255) COMMENT '会员展示图片URL',
                          user_count INT DEFAULT 0 COMMENT '用户数量',
                          job_count INT DEFAULT 0 COMMENT '招聘信息数量',

                          activity1 VARCHAR(255) COMMENT '最近活动1',
                          activity1_time DATETIME COMMENT '活动1时间',
                          activity2 VARCHAR(255) COMMENT '最近活动2',
                          activity2_time DATETIME COMMENT '活动2时间',
                          activity3 VARCHAR(255) COMMENT '最近活动3',
                          activity3_time DATETIME COMMENT '活动3时间',
                          activity4 VARCHAR(255) COMMENT '最近活动4',
                          activity4_time DATETIME COMMENT '活动4时间',
                          activity5 VARCHAR(255) COMMENT '最近活动5',
                          activity5_time DATETIME COMMENT '活动5时间',

                          update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) COMMENT='网站概览信息表';

CREATE TABLE resume (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '简历ID',
                        user_id BIGINT NOT NULL COMMENT '用户ID',

    -- 基本信息
                        name VARCHAR(255) NOT NULL COMMENT '姓名',
                        phone VARCHAR(255) COMMENT '手机号',
                        wechat VARCHAR(255) COMMENT '微信号',
                        email VARCHAR(255) COMMENT '邮箱',
                        gender VARCHAR(255) COMMENT '性别',
                        birthday VARCHAR(255) COMMENT '出生日期',
                        address VARCHAR(255) COMMENT '地址',

    -- 大段内容用 TEXT
                        education TEXT COMMENT '教育背景（学校、专业、时间等）',
                        skills TEXT COMMENT '专业技能',
                        work_experience TEXT COMMENT '工作经验',
                        project_experience TEXT COMMENT '项目经历',
                        internship_experience TEXT COMMENT '实习经历',
                        certificates TEXT COMMENT '荣誉证书',
                        self_evaluation TEXT COMMENT '自我评价',
                        create_time    DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
                        update_time    DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT='个人简历信息表';

CREATE TABLE invite_commission (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
                       user_id BIGINT NOT NULL COMMENT '邀请人用户ID，对应user表id',
                       invited_count INT NOT NULL DEFAULT 0 COMMENT '累计邀请用户数',
                       total_commission DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '累计获得佣金',
                       pending_commission DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '确认中的佣金',
                       balance_commission DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '佣金余额，可用佣金',
                       status             tinyint    default 1      not null comment '状态：0-待确认，1-已确认',
                       create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                       update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
                       UNIQUE KEY uk_user_id (user_id)
) COMMENT='邀请佣金记录表';

