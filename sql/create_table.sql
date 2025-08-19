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
    user_role     int          default 1                 not null comment '用户角色：0:管理员 1:普通用户',
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

     position_name      VARCHAR(255) COMMENT '岗位(专业/岗位名称)',
     application_status VARCHAR(100) COMMENT '投递进度',

     update_time        DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
         ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
     deadline           VARCHAR(100) COMMENT '投递截止(尽快投递/x.x截止)',

     related_link       VARCHAR(500) COMMENT '相关链接',
     announcement       TEXT COMMENT '招聘公告',
     referral_code      VARCHAR(100) COMMENT '内推码',
     remark             TEXT COMMENT '备注'
) COMMENT='招聘投递信息表' collate = utf8mb4_unicode_ci;
