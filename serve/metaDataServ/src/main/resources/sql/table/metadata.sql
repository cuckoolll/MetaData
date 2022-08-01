CREATE DATABASE metadata;
use metadata;
create table if not exists t_metadata_db (
	project_id varchar(50) primary key COMMENT '项目id',
	db_schema varchar(200) DEFAULT NULL COMMENT '数据库名称',
	db_type varchar(50) DEFAULT NULL COMMENT '数据库类型',
	remark text comment '备注',
	sort bigint DEFAULT NULL COMMENT '排序',
  	datastatusid int DEFAULT NULL COMMENT '数据状态(0:禁用、1:启用)',
	update_time timestamp NULL DEFAULT NULL COMMENT '最后变更时间',
	update_by varchar(50) DEFAULT NULL COMMENT '最后操作员',
	create_time timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	create_by varchar(50) DEFAULT NULL COMMENT '创建操作员'
) comment '数据库对象';

CREATE table if not exists `t_metadata_db_conf` (
  `db_id` varchar(50) primary key COMMENT '主键',
  `db_username` varchar(50) DEFAULT NULL COMMENT '数据库用户名',
  `db_password` varchar(50) DEFAULT NULL COMMENT '数据库密码',
  `db_type` varchar(50) DEFAULT NULL COMMENT '数据库类型',
  `db_url` varchar(2000) DEFAULT NULL COMMENT '数据库地址',
  `db_name` varchar(200) DEFAULT NULL COMMENT '数据库名称',
  `db_schema` varchar(400) DEFAULT NULL COMMENT '数据库schema',
  `sort` bigint DEFAULT NULL COMMENT '排序',
  `datastatusid` int DEFAULT NULL COMMENT '数据状态',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '最后变更时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '最后操作员',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建操作员',
  `project_id` varchar(50) DEFAULT NULL COMMENT '所属项目id'
) comment '数据库配置表';


create table if not exists t_metadata_db_table (
	table_id varchar(50) primary key comment '表id，主键',
	table_name varchar(100) comment '表名',
	table_type varchar(50) comment '表类型',
	table_catalog varchar(100) comment '表目录',
	table_schema varchar(100) comment '所属库',
	remark text comment '备注',
	sort bigint DEFAULT NULL COMMENT '排序',
  	datastatusid int DEFAULT NULL COMMENT '数据状态(0:禁用、1:启用)',
	update_time timestamp NULL DEFAULT NULL COMMENT '最后变更时间',
	update_by varchar(50) DEFAULT NULL COMMENT '最后操作员',
	create_time timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	create_by varchar(50) DEFAULT NULL COMMENT '创建操作员'
) comment '数据库表对象';

create table if not exists t_metadata_db_column (
	column_id varchar(50) primary key comment '字段id，主键',
	table_name varchar(100) comment '表名',
	table_schema varchar(100) comment '所属库',
	column_name varchar(50) comment '字段名',
	data_type varchar(50) comment '数据类型',
	column_default text comment '默认值',
	is_primary tinyint comment '是否主键（1：是，0或空：否）',
	is_nullable tinyint default 1 comment '是否可空（0：不为空，1：可为空）',
	varchar_length bigint comment '字符串字段长度',
	number_length bigint comment '数值型字段长度',
	number_scale bigint comment '数值型字段精度',
	remark text comment '备注',
	extra text comment '扩展方法',
	sort bigint DEFAULT NULL COMMENT '排序',
  	datastatusid int DEFAULT NULL COMMENT '数据状态(0:禁用、1:启用)',
	update_time timestamp NULL DEFAULT NULL COMMENT '最后变更时间',
	update_by varchar(50) DEFAULT NULL COMMENT '最后操作员',
	create_time timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	create_by varchar(50) DEFAULT NULL COMMENT '创建操作员'
) comment '数据库表字段';

create table if not exists t_metadata_db_index (
	index_id varchar(50) primary key comment '索引id，主键',
	index_name varchar(50) comment '索引名',
	table_name varchar(100) comment '表名',
	table_schema varchar(100) comment '所属库',
	is_unique tinyint comment '是否唯一索引（0：否，1：是）',
	index_schema varchar(50) comment '索引所属库',
	column_name varchar(50) comment '字段名',
	index_type varchar(50) comment '索引类型',
	sort bigint DEFAULT NULL COMMENT '排序',
  	datastatusid int DEFAULT NULL COMMENT '数据状态(0:禁用、1:启用)',
	update_time timestamp NULL DEFAULT NULL COMMENT '最后变更时间',
	update_by varchar(50) DEFAULT NULL COMMENT '最后操作员',
	create_time timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	create_by varchar(50) DEFAULT NULL COMMENT '创建操作员'
) comment '数据库表索引';


create table if not exists t_metadata_const_table (
	table_id varchar(50) primary key comment '主键',
	table_name varchar(100) comment '表名',
	table_schema varchar(100) comment '所属库',
	remark text comment '备注',
	update_time timestamp NULL DEFAULT NULL COMMENT '最后变更时间',
	update_by varchar(50) DEFAULT NULL COMMENT '最后操作员',
	create_time timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	create_by varchar(50) DEFAULT NULL COMMENT '创建操作员'
) comment '常量表对象';


create table if not exists t_metadata_opt (
	opt_id bigint primary key comment '主键',
	opt_type varchar(50) comment '操作类型',
	title text comment '标题',
	description text comment '说明',
	table_id varchar(50) comment '主键',
	table_name varchar(100) comment '表名',
	table_schema varchar(100) comment '所属库',
	remark text comment '表备注',
	target varchar(50) comment '处理人',
	step tinyint comment '步骤数',
	update_time timestamp NULL DEFAULT NULL COMMENT '最后变更时间',
	update_by varchar(50) DEFAULT NULL COMMENT '最后操作员',
	create_time timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	create_by varchar(50) DEFAULT NULL COMMENT '创建操作员'
) comment '操作记录表';

create table if not exists t_metadata_opt_column (
	column_id varchar(50) comment '字段id，主键',
	opt_id bigint comment '外键关联t_metadata_opt的opt_id',
	opt_type varchar(50) comment '操作类型',
	table_name varchar(100) comment '表名',
	table_schema varchar(100) comment '所属库',
	column_name varchar(50) comment '字段名',
	data_type varchar(50) comment '数据类型',
	column_default text comment '默认值',
	is_primary tinyint comment '是否主键（1：是，0或空：否）',
	is_nullable tinyint default 1 comment '是否可空（0：不为空，1：可为空）',
	varchar_length bigint comment '字符串字段长度',
	number_length bigint comment '数值型字段长度',
	number_scale bigint comment '数值型字段精度',
	remark text comment '备注',
	extra text comment '扩展方法',
	sort bigint DEFAULT NULL COMMENT '排序',
	update_time timestamp NULL DEFAULT NULL COMMENT '最后变更时间',
	update_by varchar(50) DEFAULT NULL COMMENT '最后操作员',
	create_time timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	create_by varchar(50) DEFAULT NULL COMMENT '创建操作员'
) comment '字段操作记录';

create table if not exists t_metadata_opt_index (
	index_id varchar(50) primary key comment '索引id，主键',
	opt_id bigint comment '外键关联t_metadata_opt的opt_id',
	opt_type varchar(50) comment '操作类型',
	index_name varchar(50) comment '索引名',
	table_name varchar(100) comment '表名',
	table_schema varchar(100) comment '所属库',
	is_unique tinyint comment '是否唯一索引（0：否，1：是）',
	index_schema varchar(50) comment '索引所属库',
	column_name varchar(50) comment '字段名',
	index_type varchar(50) comment '索引类型',
	update_time timestamp NULL DEFAULT NULL COMMENT '最后变更时间',
	update_by varchar(50) DEFAULT NULL COMMENT '最后操作员',
	create_time timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	create_by varchar(50) DEFAULT NULL COMMENT '创建操作员'
) comment '索引操作记录';


create table if not exists t_metadata_opt_column_alter (
	opt_id bigint comment '外键关联t_metadata_opt的opt_id',
	column_id varchar(50) comment '字段id',
	opt_type varchar(50) comment '操作类型'
) comment '字段变更记录';

CREATE TABLE `oauth_client_details` (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `client_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键(客户端ID)',
  `resource_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '资源id集合(为空则能访问所有资源)',
  `client_secret` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '访问密匙',
  `scope` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '权限范围',
  `authorized_grant_types` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '授权类型',
  `web_server_redirect_uri` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '重定向URI',
  `authorities` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '指定权限值',
  `access_token_validity` int DEFAULT NULL COMMENT '访问token有效时间值(单位:秒)',
  `refresh_token_validity` int DEFAULT NULL COMMENT '刷新token有效时间值(单位:秒)',
  `additional_information` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '预留字段(JSON格式)',
  `autoapprove` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '自动审核',
  `app_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '应用ID',
  PRIMARY KEY (`id`) USING BTREE
) COMMENT='oauth2认证配置';

create table if not exists t_metadata_user (
	user_id varchar(50) primary key comment '用户id，主键',
	username varchar(50) comment '用户名',
	nick_name varchar(50) comment '用户名',
 	password varchar(100) comment '密码',
 	expired_time timestamp comment '过期时间',
 	pwd_reset_time timestamp comment '密码重置时间',
 	phone varchar(50) comment '手机号',
 	email varchar(50) comment '邮箱',
 	roles varchar(50) comment '角色',
 	client_id varchar(50) comment 'client_id',
 	sort bigint DEFAULT NULL COMMENT '排序',
  	datastatusid int DEFAULT NULL COMMENT '数据状态(0:禁用、1:启用)',
	update_time timestamp NULL DEFAULT NULL COMMENT '最后变更时间',
	update_by varchar(50) DEFAULT NULL COMMENT '最后操作员',
	create_time timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	create_by varchar(50) DEFAULT NULL COMMENT '创建操作员'
) comment '用户表';
