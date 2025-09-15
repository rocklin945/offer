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
                          original_price decimal(10, 2) default 0.00     null comment '原价',
                          current_price  decimal(10, 2) default 0.00     null comment '现价',
                          commission_rate decimal(5, 2)  default 0.00    null comment '佣金比例(%)',

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

    -- 通用基本信息
                        name VARCHAR(255) NOT NULL COMMENT '姓名',
                        phone VARCHAR(255) COMMENT '手机号',
                        wechat VARCHAR(255) COMMENT '微信号',
                        email VARCHAR(255) COMMENT '邮箱',
                        gender VARCHAR(50) COMMENT '性别',
                        birthday VARCHAR(50) COMMENT '出生日期',
                        address VARCHAR(255) COMMENT '地址',
                        id_number VARCHAR(100) COMMENT '证件号码',

    -- 央国企特有基本信息
                        gov_current_residence VARCHAR(255) COMMENT '现居住地',
                        gov_registered_residence VARCHAR(255) COMMENT '户口所在地',
                        gov_native_place VARCHAR(255) COMMENT '籍贯',
                        gov_gaokao_place VARCHAR(255) COMMENT '高考所在地',
                        gov_political_status VARCHAR(100) COMMENT '政治面貌',
                        gov_emergency_name VARCHAR(255) COMMENT '紧急联络人姓名',
                        gov_emergency_phone VARCHAR(100) COMMENT '紧急联络人手机号',
                        gov_emergency_relation VARCHAR(100) COMMENT '紧急联络人与本人关系',

    -- 银行特有基本信息
                        bank_political_status VARCHAR(100) COMMENT '政治面貌',
                        bank_height VARCHAR(20) COMMENT '身高',
                        bank_weight VARCHAR(20) COMMENT '体重',
                        bank_health VARCHAR(100) COMMENT '健康状况',
                        bank_marriage_status VARCHAR(50) COMMENT '婚姻状况',
                        bank_child_status VARCHAR(50) COMMENT '生育状况',
                        bank_info_channel VARCHAR(255) COMMENT '获取信息渠道',
                        bank_emergency_name VARCHAR(255) COMMENT '紧急联络人姓名',
                        bank_emergency_phone VARCHAR(100) COMMENT '紧急联络人手机号',
                        bank_emergency_relation VARCHAR(100) COMMENT '紧急联络人与本人关系',

    -- 教育经历（民企、央国企、银行共用）
                        edu_experiences JSON COMMENT '教育经历列表（JSON存储多条）',

    -- 实习经历（民企）
                        private_internship TEXT COMMENT '实习经历（JSON存多条）',

    -- 校园实践经历（央国企）
                        gov_campus_practice TEXT COMMENT '校园实践经历（JSON存多条）',

    -- 工作经历（央国企）
                        gov_work_experience TEXT COMMENT '工作经历（JSON存多条）',

    -- 项目经历（民企）
                        private_project_experience TEXT COMMENT '项目经历（JSON存多条）',

    -- 在校经历（银行）
                        bank_school_experience TEXT COMMENT '在校经历（JSON存多条）',

    -- 语言水平（共用）
                        language_level TEXT COMMENT '语言水平（JSON存多条）',

    -- 荣誉 & 证书（共用）
                        honors TEXT COMMENT '荣誉',
                        certificates TEXT COMMENT '证书',

    -- 技能水平（共用）
                        skill_level TEXT COMMENT '技能水平（JSON存多条）',

    -- 论文（民企）
                        private_paper TEXT COMMENT '论文（JSON存多条）',

    -- 竞赛（民企）
                        private_competition TEXT COMMENT '竞赛',

    -- 家庭情况（央国企 & 银行）
                        family_info TEXT COMMENT '家庭情况（JSON存多条）',

    -- 薪酬福利（银行）
                        bank_salary_expectation VARCHAR(100) COMMENT '期望薪酬',
                        bank_expected_location VARCHAR(255) COMMENT '期望工作地点',

    -- 职业资格认证（银行）
                        bank_professional_cert TEXT COMMENT '职业资格认证（JSON存多条）',

    -- 计算机技能（银行）
                        bank_computer_skills TEXT COMMENT '计算机技能',

    -- 培训经历（银行）
                        bank_training_experience TEXT COMMENT '培训经历（JSON存多条）',

    -- 奖惩情况（银行）
                        bank_rewards_punishments TEXT COMMENT '奖惩情况（JSON存多条）',

    -- 自我评价（共用）
                        self_evaluation TEXT COMMENT '自我评价',

    -- 时间字段
                        create_time DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
                        update_time DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT='个人简历信息表（民企/央国企/银行）';


drop table resume;

CREATE TABLE invite_commission (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
                       user_id BIGINT NOT NULL COMMENT '邀请人用户ID，对应user表id',
                       invited_count INT NOT NULL DEFAULT 0 COMMENT '累计邀请用户数',
                       total_commission DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '累计获得佣金',
                       pending_commission DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '确认中的佣金',
                       balance_commission DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '佣金余额，可用佣金',
                       status             tinyint    default 0      not null comment '状态：0-待确认，1-已确认，2-已拒绝',
                       create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                       update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
                       UNIQUE KEY uk_user_id (user_id)
) COMMENT='邀请佣金记录表';

CREATE TABLE membership_plan (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
                                 label VARCHAR(50) NOT NULL COMMENT '套餐名称，比如周卡、月卡',
                                 price DECIMAL(10,2) NOT NULL COMMENT '套餐价格',
                                 days INT NOT NULL COMMENT '有效天数',
                                 create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
                                 status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0=下架，1=上架'
) COMMENT='会员套餐表';

-- 笔试面试资料表
CREATE TABLE IF NOT EXISTS material (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
                                 file_name VARCHAR(200) NOT NULL COMMENT '文件名',
                                 file_uuid VARCHAR(500) NOT NULL COMMENT '文件uuid',
                                 category VARCHAR(100) NOT NULL COMMENT '资料分类',
                                 file_size BIGINT COMMENT '文件大小(字节)',
                                 total_pages INT COMMENT '总页数',
                                 view_count INT DEFAULT 0 COMMENT '查看次数',
                                 create_time DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
                                 update_time DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                 is_delete TINYINT DEFAULT 0 NOT NULL COMMENT '是否删除',
                                 INDEX idx_category (category),
                                 INDEX idx_file_uuid (file_uuid)
) COMMENT '笔试面试资料表';

CREATE TABLE `pay_order` (
                             `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
                             `out_trade_no` VARCHAR(64) NOT NULL COMMENT '商户订单号（你系统生成，唯一）',
                             `trade_no` VARCHAR(64) DEFAULT NULL COMMENT '支付平台订单号',
                             `user_id` BIGINT NOT NULL COMMENT '下单用户ID',
                             `name` VARCHAR(100) NOT NULL COMMENT '商品名称',
                             `money` DECIMAL(10,2) NOT NULL COMMENT '订单金额',
                             `type` VARCHAR(10) NOT NULL COMMENT '支付方式(alipay / wxpay)',
                             `status` TINYINT NOT NULL DEFAULT 0 COMMENT '支付状态 0未支付 1已支付 2退款中 3已退款',
                             `param` VARCHAR(255) DEFAULT NULL COMMENT '扩展参数（回调会原样返回）',
                             `notify_time` DATETIME DEFAULT NULL COMMENT '支付平台通知时间',
                             `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单创建时间',
                             `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '订单更新时间',
                             UNIQUE KEY `uk_out_trade_no` (`out_trade_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付订单表';

-- 卡密商家表
CREATE TABLE IF NOT EXISTS code_merchant (
                                id BIGINT PRIMARY KEY COMMENT '主键ID',
                                account VARCHAR(255) NOT NULL COMMENT '卡密商家账号',
                                password VARCHAR(255) NOT NULL COMMENT '密码',
                                autumn_price DECIMAL(10,2) NOT NULL COMMENT '秋招会员价格',
                                campus_price DECIMAL(10,2) NOT NULL COMMENT '校招会员价格',
                                create_time DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
                                update_time DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                is_delete TINYINT DEFAULT 0 NOT NULL COMMENT '是否删除'
) COMMENT='卡密商家表';

-- 卡密表
CREATE TABLE IF NOT EXISTS code (
                                id BIGINT PRIMARY KEY COMMENT '主键ID',
                                code VARCHAR(255) NOT NULL COMMENT '卡密',
                                merchant_id BIGINT NOT NULL COMMENT '关联的商家ID',
                                is_used TINYINT DEFAULT 0 NOT NULL COMMENT '是否已使用：0-未使用，1-已使用',
                                user_account BIGINT COMMENT '使用者账号',
                                used_time DATETIME COMMENT '使用时间',
                                create_time DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
                                update_time DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                is_delete TINYINT DEFAULT 0 NOT NULL COMMENT '是否删除',
                                UNIQUE KEY uk_code (code),
                                INDEX idx_product_id (merchant_id)
) COMMENT='卡密表';