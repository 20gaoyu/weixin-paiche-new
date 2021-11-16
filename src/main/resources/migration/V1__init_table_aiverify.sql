/*
 Navicat Premium Data Transfer

 Source Server         : 贵州mysql
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : 192.168.8.27:8306
 Source Schema         : aiverify

 Target Server Version : 50723
 File Encoding         : 65001

 Date: 11/09/2019 09:36:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_m_account
-- ----------------------------
CREATE TABLE `t_m_account`  (
  `account_id` bigint(20) NOT NULL COMMENT '账号标识',
  `account_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账号名称',
  `account_type` tinyint(4) NULL DEFAULT 2 COMMENT '类别',
  `password` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `salt` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'salt',
  `role_id` bigint(20) NOT NULL COMMENT '角色标识',
  `role_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `customer_id` bigint(20) NOT NULL COMMENT '客户标识',
  `customer_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户名称',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `last_login_time` timestamp(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  `completed_guide` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '已完成的用户指引功能清单(逗号分割)',
  `if_reset_passwd` tinyint(4) NULL DEFAULT 0 COMMENT '是否重置密码',
  PRIMARY KEY (`account_id`) USING BTREE,
  INDEX `idx_t_m_account`(`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '账号表' ROW_FORMAT = Dynamic;



-- ----------------------------
-- Table structure for t_m_customer
-- ----------------------------

CREATE TABLE `t_m_customer`  (
  `customer_id` bigint(20) NOT NULL COMMENT '客户标识',
  `customer_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户名称',
  `customer_source` int(11) NULL DEFAULT NULL COMMENT '客户来源，0：CDN,1：媒体存储，2管理员',
  `account` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主账号',
  `app_id` bigint(20) NULL DEFAULT NULL COMMENT 'APP ID',
  `api_key` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'API KEY',
  `secret_key` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'SECRET KEY',
  `email` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'EMAIL',
  `telephone` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `customer_describe` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户描述',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态：0：无效；1：有效',
  `creator` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`customer_id`) USING BTREE,
  INDEX `index_name`(`api_key`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '客户表' ROW_FORMAT = Dynamic;




-- ----------------------------
-- Table structure for t_m_menu
-- ----------------------------

CREATE TABLE `t_m_menu`  (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `menu_type` tinyint(4) NULL DEFAULT NULL COMMENT '菜单类型',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父级菜单ID,-1:一级目录；1：二级目录',
  `menu_order` int(11) NULL DEFAULT NULL COMMENT '排序',
  `if_show` tinyint(4) NULL DEFAULT 0 COMMENT '是否可用，0：不可用，1：可用',
  `url` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'URL',
  `method` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '方法',
  `operation` int(11) NULL DEFAULT NULL COMMENT '操作类型，0：不能增加不能删除，1：只能增加不能删除，2：可以删除不能增加',
  `creator` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 154 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '菜单表' ROW_FORMAT = Dynamic;




-- ----------------------------
-- Table structure for t_m_role
-- ----------------------------

CREATE TABLE `t_m_role`  (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `role_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `category` int(11) NULL DEFAULT NULL COMMENT '角色类别：0：无效，1：普通，2：媒体存储，3：CDN，4：管理员,5：超级管理员',
  `creator` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_m_role_menu
-- ----------------------------

CREATE TABLE `t_m_role_menu`  (
  `role_menu_id` bigint(20) NOT NULL,
  `menu_id` int(11) NULL DEFAULT NULL,
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色ID',
  `creator` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`role_menu_id`) USING BTREE,
  INDEX `index_name`(`menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for t_m_visit_log
-- ----------------------------

CREATE TABLE `t_m_visit_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '标识',
  `day_id` int(10) NOT NULL COMMENT '日标识',
  `account_id` bigint(20) NOT NULL COMMENT '账号标识',
  `account_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账号名称',
  `customer_id` bigint(20) NOT NULL COMMENT '客户标识',
  `customer_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户名称',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `visit_time` timestamp(0) NOT NULL COMMENT '访问时间',
  `visitor_ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '访问者的IP',
  `visit_url` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '访问的url地址',
  `request_content` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '访问日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_m_error_log
-- ----------------------------

CREATE TABLE `t_m_error_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '标识',
  `day_id` int(10) NOT NULL COMMENT '日标识',
  `account_id` bigint(20) DEFAULT NULL COMMENT '账号标识',
  `account_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '账号名称',
  `customer_id` bigint(20) DEFAULT NULL COMMENT '客户标识',
  `customer_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '客户名称',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `error_time` timestamp(0) NOT NULL COMMENT '异常时间',
  `error_desc` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '错误描述',
  `error_code` int(10) NULL DEFAULT 0 COMMENT '错误码',
  `error_content` varchar(6000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '错误内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 58 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '错误日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_m_api_log
-- ----------------------------
CREATE TABLE `t_m_api_log`  (
  `api_log_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `customer_id` bigint(32) UNSIGNED NOT NULL DEFAULT 0 COMMENT '客户Id',
  `customer_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '客户名',
  `task_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '任务Id',
  `api_type` tinyint(5) NOT NULL COMMENT 'api类型',
  `req_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '请求url',
  `req_body` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '请求body',
  `req_method` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '请求method',
  `source_ip` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '来源IP',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '信息添加或编辑的时间',
  PRIMARY KEY (`api_log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2395 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户操作日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_m_api_log_detail
-- ----------------------------
CREATE TABLE `t_m_api_log_detail`  (
  `api_log_detail_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `api_log_id` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'api日志id',
  `req_body` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '请求body',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '信息添加或编辑的时间',
  PRIMARY KEY (`api_log_detail_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2382 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户操作日志' ROW_FORMAT = Dynamic;

CREATE TABLE `t_m_member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '账号标识',
  `account_name` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '账号名称',
  `job_number` int(4) DEFAULT '2' COMMENT '工号',
  `sex` varchar(8) CHARACTER SET utf8 DEFAULT NULL COMMENT '性别',
  `telephone` varchar(16) CHARACTER SET utf8 DEFAULT NULL COMMENT '手机',
  `email` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '邮箱',
  `role_id` bigint(20) NOT NULL COMMENT '角色标识',
  `role_name` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '角色名称',
  `department_id` bigint(20) NOT NULL COMMENT '部门',
  `department_name` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '部门名称',
  `position` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '职位',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_login_time` timestamp NULL DEFAULT NULL COMMENT '最后登录时间',
  `open_id` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT 'openid',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_t_m_account` (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='用户表';

CREATE TABLE `t_m_department` (
 `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '账号标识',
 `department_id` bigint(20) NOT NULL COMMENT '部门',
 `department_name` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '部门名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='部门表';

CREATE TABLE `t_m_car` (
 `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '账号标识',
 `license` varchar(64) NOT NULL COMMENT '车牌',
 `brand` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '品牌',
 `type` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '型号',
 `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
 `mileage` bigint(20) NOT NULL COMMENT '里程',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='部门表';

CREATE TABLE `t_m_dispatch_car_detail` (
 `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '账号标识',
 `applicant` varchar(64) NOT NULL COMMENT '申请人',
 `user` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '使用者',
 `start_time` timestamp NOT NULL COMMENT '开始时间',
 `end_time` timestamp NOT NULL COMMENT '结束时间',
 `if_comment` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '是否评价',
 `use_reason` varchar(128) CHARACTER SET utf8 NOT NULL COMMENT '用车原因',
 `destination` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '目的地',
 `one_audit` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '一级审核',
 `two_audit` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '二级审核',
 `status` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '审核状态',
 `operation` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '操作',
 `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='用车详单表';

SET FOREIGN_KEY_CHECKS = 1;
