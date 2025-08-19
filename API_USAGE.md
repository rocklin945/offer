# 招聘信息查询网站 API 使用说明

## 项目概述
基于Spring Boot + MyBatis的招聘信息管理系统，提供招聘信息的增删改查功能。

## 主要功能
1. 添加招聘信息（ID自动生成使用UUID_SHORT()）
2. 删除招聘信息  
3. 更新招聘信息
4. 查询招聘信息详情
5. 分页查询招聘信息列表（支持多条件过滤和排序）

## API 接口说明

### 1. 添加招聘信息
- **接口地址**: `POST /api/jobInfo/add`
- **请求参数**: JobInfoAddRequest
- **响应**: BaseResponse<Boolean>
- **说明**: ID会自动使用MySQL的UUID_SHORT()函数生成

```json
{
  "companyName": "腾讯科技",
  "companyType": "互联网公司",
  "industry": "互联网",
  "recruitType": "校招",
  "workLocation": "深圳",
  "recruitTarget": "2024年毕业",
  "positionName": "Java开发工程师",
  "applicationStatus": "待投递",
  "startTime": "2024-01-01T00:00:00",
  "deadline": "2024-03-31截止",
  "relatedLink": "https://careers.tencent.com",
  "announcement": "招聘Java开发工程师...",
  "referralCode": "REF123456",
  "remark": "优先考虑有实习经验的候选人"
}
```

### 2. 删除招聘信息
- **接口地址**: `POST /api/jobInfo/delete`
- **请求参数**: DeleteRequest
- **响应**: BaseResponse<Boolean>

```json
{
  "id": 1
}
```

### 3. 更新招聘信息
- **接口地址**: `POST /api/jobInfo/update`
- **请求参数**: JobInfoUpdateRequest
- **响应**: BaseResponse<Boolean>

```json
{
  "id": 1,
  "companyName": "腾讯科技",
  "applicationStatus": "已投递"
}
```

### 4. 根据ID获取招聘信息
- **接口地址**: `GET /api/jobInfo/get/{id}`
- **路径参数**: id (Long)
- **响应**: BaseResponse<JobInfo>

### 5. 分页查询招聘信息列表
- **接口地址**: `POST /api/jobInfo/list`
- **请求参数**: JobInfoQueryRequest
- **响应**: BaseResponse<PageResponse<JobInfo>>

```json
{
  "pageNum": 1,
  "pageSize": 10,
  "sortField": "startTime",
  "sortOrder": "desc",
  "companyName": "腾讯",
  "recruitType": "校招",
  "workLocation": "深圳",
  "positionName": "Java",
  "applicationStatus": "待投递",
  "startTimeBegin": "2024-01-01T00:00:00",
  "startTimeEnd": "2024-12-31T23:59:59",
  "deadline": "2024-03-31"
}
```

### 查询条件说明
- `pageNum`: 页码（默认1）
- `pageSize`: 每页大小（默认10）
- `sortField`: 排序字段，支持以下值：
  - `companyName`: 公司名称
  - `recruitType`: 招聘类型
  - `workLocation`: 工作地点
  - `positionName`: 岗位名称
  - `applicationStatus`: 投递进度
  - `startTime`: 开始时间（默认）
  - `deadline`: 投递截止
  - `id`: 主键ID
- `sortOrder`: 排序规则（`asc`升序，`desc`降序，默认降序）
- `companyName`: 公司名称（模糊查询）
- `recruitType`: 招聘类型（精确匹配）
- `workLocation`: 工作地点（模糊查询）
- `positionName`: 岗位名称（模糊查询）
- `applicationStatus`: 投递进度（精确匹配）
- `startTimeBegin`: 开始时间范围-起始时间
- `startTimeEnd`: 开始时间范围-结束时间
- `deadline`: 投递截止（模糊查询）

## 数据库表结构

### job_info 表字段说明
- `id`: 主键ID（使用UUID_SHORT()自动生成）
- `company_name`: 公司名称
- `company_type`: 公司类型
- `industry`: 所属行业
- `recruit_type`: 招聘类型(校招/社招/实习)
- `work_location`: 工作地点
- `recruit_target`: 招聘对象(20xx年毕业)
- `position_name`: 岗位(专业/岗位名称)
- `application_status`: 投递进度
- `start_time`: 开始时间
- `deadline`: 投递截止(尽快投递/x.x截止)
- `related_link`: 相关链接
- `announcement`: 招聘公告
- `referral_code`: 内推码
- `remark`: 备注

## 启动项目
1. 确保MySQL数据库已启动
2. 执行 `sql/create_table.sql` 创建数据库和表
3. 配置 `application.yml` 中的数据库连接信息
4. 运行 `mvn spring-boot:run` 启动项目
5. 访问 `http://localhost:8081/api/doc.html` 查看Swagger文档

## 技术栈
- Spring Boot 3.x
- MyBatis
- MySQL
- Swagger/Knife4j
- Lombok

## 特性
- ✅ ID使用UUID_SHORT()自动生成
- ✅ 支持多条件分页查询
- ✅ 支持时间范围查询
- ✅ 支持多字段排序（升序/降序）
- ✅ 统一响应格式
- ✅ 参数校验
- ✅ Swagger文档

## 排序示例
```json
// 按公司名称升序排列
{
  "pageNum": 1,
  "pageSize": 10,
  "sortField": "companyName",
  "sortOrder": "asc"
}

// 按开始时间降序排列（默认）
{
  "pageNum": 1,
  "pageSize": 10,
  "sortField": "startTime",
  "sortOrder": "desc"
}

// 按投递进度升序，同时过滤条件
{
  "pageNum": 1,
  "pageSize": 10,
  "sortField": "applicationStatus",
  "sortOrder": "asc",
  "recruitType": "校招",
  "workLocation": "深圳"
}