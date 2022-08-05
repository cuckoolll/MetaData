CREATE DATABASE metadata;
use metadata;
create table if not exists t_metadata_db (
	project_id varchar(50) primary key COMMENT '��Ŀid',
	db_schema varchar(200) DEFAULT NULL COMMENT '���ݿ�����',
	db_type varchar(50) DEFAULT NULL COMMENT '���ݿ�����',
	remark text comment '��ע',
	sort bigint DEFAULT NULL COMMENT '����',
  	datastatusid int DEFAULT NULL COMMENT '����״̬(0:���á�1:����)',
	update_time timestamp NULL DEFAULT NULL COMMENT '�����ʱ��',
	update_by varchar(50) DEFAULT NULL COMMENT '������Ա',
	create_time timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
	create_by varchar(50) DEFAULT NULL COMMENT '��������Ա'
) comment '���ݿ����';

CREATE table if not exists `t_metadata_db_conf` (
  `db_id` varchar(50) primary key COMMENT '����',
  `db_username` varchar(50) DEFAULT NULL COMMENT '���ݿ��û���',
  `db_password` varchar(50) DEFAULT NULL COMMENT '���ݿ�����',
  `db_type` varchar(50) DEFAULT NULL COMMENT '���ݿ�����',
  `db_url` varchar(2000) DEFAULT NULL COMMENT '���ݿ��ַ',
  `db_name` varchar(200) DEFAULT NULL COMMENT '���ݿ�����',
  `db_schema` varchar(400) DEFAULT NULL COMMENT '���ݿ�schema',
  `sort` bigint DEFAULT NULL COMMENT '����',
  `datastatusid` int DEFAULT NULL COMMENT '����״̬',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '�����ʱ��',
  `update_by` varchar(50) DEFAULT NULL COMMENT '������Ա',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `create_by` varchar(50) DEFAULT NULL COMMENT '��������Ա',
  `project_id` varchar(50) DEFAULT NULL COMMENT '������Ŀid'
) comment '���ݿ����ñ�';


create table if not exists t_metadata_db_table (
	table_id varchar(50) primary key comment '��id������',
	table_name varchar(100) comment '����',
	table_type varchar(50) comment '������',
	table_catalog varchar(100) comment '��Ŀ¼',
	table_schema varchar(100) comment '������',
	remark text comment '��ע',
	sort bigint DEFAULT NULL COMMENT '����',
  	datastatusid int DEFAULT NULL COMMENT '����״̬(0:���á�1:����)',
	update_time timestamp NULL DEFAULT NULL COMMENT '�����ʱ��',
	update_by varchar(50) DEFAULT NULL COMMENT '������Ա',
	create_time timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
	create_by varchar(50) DEFAULT NULL COMMENT '��������Ա'
) comment '���ݿ�����';

create table if not exists t_metadata_db_column (
	column_id varchar(50) primary key comment '�ֶ�id������',
	table_name varchar(100) comment '����',
	table_schema varchar(100) comment '������',
	column_name varchar(50) comment '�ֶ���',
	data_type varchar(50) comment '��������',
	column_default text comment 'Ĭ��ֵ',
	is_primary tinyint comment '�Ƿ�������1���ǣ�0��գ���',
	is_nullable tinyint default 1 comment '�Ƿ�ɿգ�0����Ϊ�գ�1����Ϊ�գ�',
	varchar_length bigint comment '�ַ����ֶγ���',
	number_length bigint comment '��ֵ���ֶγ���',
	number_scale bigint comment '��ֵ���ֶξ���',
	remark text comment '��ע',
	extra text comment '��չ����',
	sort bigint DEFAULT NULL COMMENT '����',
  	datastatusid int DEFAULT NULL COMMENT '����״̬(0:���á�1:����)',
	update_time timestamp NULL DEFAULT NULL COMMENT '�����ʱ��',
	update_by varchar(50) DEFAULT NULL COMMENT '������Ա',
	create_time timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
	create_by varchar(50) DEFAULT NULL COMMENT '��������Ա'
) comment '���ݿ���ֶ�';

create table if not exists t_metadata_db_index (
	index_id varchar(50) primary key comment '����id������',
	index_name varchar(50) comment '������',
	table_name varchar(100) comment '����',
	table_schema varchar(100) comment '������',
	is_unique tinyint comment '�Ƿ�Ψһ������0����1���ǣ�',
	index_schema varchar(50) comment '����������',
	column_name varchar(50) comment '�ֶ���',
	index_type varchar(50) comment '��������',
	sort bigint DEFAULT NULL COMMENT '����',
  	datastatusid int DEFAULT NULL COMMENT '����״̬(0:���á�1:����)',
	update_time timestamp NULL DEFAULT NULL COMMENT '�����ʱ��',
	update_by varchar(50) DEFAULT NULL COMMENT '������Ա',
	create_time timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
	create_by varchar(50) DEFAULT NULL COMMENT '��������Ա'
) comment '���ݿ������';


create table if not exists t_metadata_const_table (
	table_id varchar(50) primary key comment '����',
	table_name varchar(100) comment '����',
	table_schema varchar(100) comment '������',
	remark text comment '��ע',
	update_time timestamp NULL DEFAULT NULL COMMENT '�����ʱ��',
	update_by varchar(50) DEFAULT NULL COMMENT '������Ա',
	create_time timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
	create_by varchar(50) DEFAULT NULL COMMENT '��������Ա'
) comment '���������';


create table if not exists t_metadata_opt (
	opt_id bigint primary key comment '����',
	opt_type varchar(50) comment '��������',
	title text comment '����',
	description text comment '˵��',
	table_id varchar(50) comment '����',
	table_name varchar(100) comment '����',
	table_schema varchar(100) comment '������',
	remark text comment '��ע',
	target varchar(50) comment '������',
	step_id bigint comment '����id',
	step_version int comment '����汾',
	status tinyint comment '״̬��0��������1������ɣ�',
	update_time timestamp NULL DEFAULT NULL COMMENT '�����ʱ��',
	update_by varchar(50) DEFAULT NULL COMMENT '������Ա',
	create_time timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
	create_by varchar(50) DEFAULT NULL COMMENT '��������Ա'
) comment '������¼��';

create table if not exists t_metadata_opt_column (
	column_id varchar(50) comment '�ֶ�id������',
	opt_id bigint comment '�������t_metadata_opt��opt_id',
	opt_type varchar(50) comment '��������',
	table_name varchar(100) comment '����',
	table_schema varchar(100) comment '������',
	column_name varchar(50) comment '�ֶ���',
	data_type varchar(50) comment '��������',
	column_default text comment 'Ĭ��ֵ',
	is_primary tinyint comment '�Ƿ�������1���ǣ�0��գ���',
	is_nullable tinyint default 1 comment '�Ƿ�ɿգ�0����Ϊ�գ�1����Ϊ�գ�',
	varchar_length bigint comment '�ַ����ֶγ���',
	number_length bigint comment '��ֵ���ֶγ���',
	number_scale bigint comment '��ֵ���ֶξ���',
	remark text comment '��ע',
	extra text comment '��չ����',
	sort bigint DEFAULT NULL COMMENT '����',
	update_time timestamp NULL DEFAULT NULL COMMENT '�����ʱ��',
	update_by varchar(50) DEFAULT NULL COMMENT '������Ա',
	create_time timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
	create_by varchar(50) DEFAULT NULL COMMENT '��������Ա'
) comment '�ֶβ�����¼';

create table if not exists t_metadata_opt_index (
	index_id varchar(50) primary key comment '����id������',
	opt_id bigint comment '�������t_metadata_opt��opt_id',
	opt_type varchar(50) comment '��������',
	index_name varchar(50) comment '������',
	table_name varchar(100) comment '����',
	table_schema varchar(100) comment '������',
	is_unique tinyint comment '�Ƿ�Ψһ������0����1���ǣ�',
	index_schema varchar(50) comment '����������',
	column_name varchar(50) comment '�ֶ���',
	index_type varchar(50) comment '��������',
	update_time timestamp NULL DEFAULT NULL COMMENT '�����ʱ��',
	update_by varchar(50) DEFAULT NULL COMMENT '������Ա',
	create_time timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
	create_by varchar(50) DEFAULT NULL COMMENT '��������Ա'
) comment '����������¼';


create table if not exists t_metadata_opt_column_alter (
	opt_id bigint comment '�������t_metadata_opt��opt_id',
	column_id varchar(50) comment '�ֶ�id',
	opt_type varchar(50) comment '��������'
) comment '�ֶα����¼';

CREATE TABLE `oauth_client_details` (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `client_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '����(�ͻ���ID)',
  `resource_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '��Դid����(Ϊ�����ܷ���������Դ)',
  `client_secret` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '�����ܳ�',
  `scope` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'Ȩ�޷�Χ',
  `authorized_grant_types` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '��Ȩ����',
  `web_server_redirect_uri` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '�ض���URI',
  `authorities` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'ָ��Ȩ��ֵ',
  `access_token_validity` int DEFAULT NULL COMMENT '����token��Чʱ��ֵ(��λ:��)',
  `refresh_token_validity` int DEFAULT NULL COMMENT 'ˢ��token��Чʱ��ֵ(��λ:��)',
  `additional_information` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'Ԥ���ֶ�(JSON��ʽ)',
  `autoapprove` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '�Զ����',
  `app_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'Ӧ��ID',
  PRIMARY KEY (`id`) USING BTREE
) COMMENT='oauth2��֤����';

create table if not exists t_metadata_user (
	user_id varchar(50) primary key comment '�û�id������',
	username varchar(50) comment '�û���',
	nick_name varchar(50) comment '�ǳ�',
 	password varchar(100) comment '����',
 	expired_time timestamp comment '����ʱ��',
 	pwd_reset_time timestamp comment '��������ʱ��',
 	phone varchar(50) comment '�ֻ���',
 	email varchar(50) comment '����',
 	role_id varchar(50) comment '��ɫid',
 	client_id varchar(50) comment 'client_id',
 	sort bigint DEFAULT NULL COMMENT '����',
  	datastatusid int DEFAULT NULL COMMENT '����״̬(0:���á�1:����)',
	update_time timestamp NULL DEFAULT NULL COMMENT '�����ʱ��',
	update_by varchar(50) DEFAULT NULL COMMENT '������Ա',
	create_time timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
	create_by varchar(50) DEFAULT NULL COMMENT '��������Ա'
) comment '�û���';

create table if not exists t_metadata_role (
    role_id varchar(50) primary key comment '��ɫid������',
    role_name varchar(50) comment '��ɫ����',
    description text comment '����',
    sort bigint DEFAULT NULL COMMENT '����',
    datastatusid int DEFAULT NULL COMMENT '����״̬(0:���á�1:����)',
    update_time timestamp NULL DEFAULT NULL COMMENT '�����ʱ��',
    update_by varchar(50) DEFAULT NULL COMMENT '������Ա',
    create_time timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
    create_by varchar(50) DEFAULT NULL COMMENT '��������Ա'
) comment '��ɫ��';

create table if not exists t_metadata_menu_item (
    item_id varchar(50) primary key comment '����',
    item_name varchar(100) comment '��������',
    html_id varchar(100) comment 'ǰ��id',
    menu_uri varchar(400) comment '�˵�uri',
    description text comment '����',
    sort bigint DEFAULT NULL COMMENT '����',
    datastatusid int DEFAULT NULL COMMENT '����״̬(0:���á�1:����)',
    update_time timestamp NULL DEFAULT NULL COMMENT '�����ʱ��',
    update_by varchar(50) DEFAULT NULL COMMENT '������Ա',
    create_time timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
    create_by varchar(50) DEFAULT NULL COMMENT '��������Ա'
) comment '�˵����ܱ�';

create table if not exists t_metadata_role_rel (
    rel_id varchar(50) primary key comment '����',
    role_id varchar(50) comment '��ɫid',
    item_id varchar(50) comment '����id',
    step_id varchar(50) comment '����id',
    rel_type tinyint comment '��ϵ���ͣ�0�����ܣ�1�����̣�',
    sort bigint DEFAULT NULL COMMENT '����',
    datastatusid int DEFAULT NULL COMMENT '����״̬(0:���á�1:����)',
    update_time timestamp NULL DEFAULT NULL COMMENT '�����ʱ��',
    update_by varchar(50) DEFAULT NULL COMMENT '������Ա',
    create_time timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
    create_by varchar(50) DEFAULT NULL COMMENT '��������Ա'
) comment '��ɫ��ϵ��';

create table if not exists t_metadata_step_conf (
    step_id bigint primary key auto_increment comment '����',
    step_name varchar(50) comment '��������',
    version int comment '�汾',
    sort bigint DEFAULT NULL COMMENT '����',
    datastatusid int DEFAULT NULL COMMENT '����״̬(0:���á�1:����)',
    update_time timestamp NULL DEFAULT NULL COMMENT '�����ʱ��',
    update_by varchar(50) DEFAULT NULL COMMENT '������Ա',
    create_time timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
    create_by varchar(50) DEFAULT NULL COMMENT '��������Ա'
) comment '����������ñ�';

create table if not exists t_metadata_step_next (
    next_id bigint primary key auto_increment comment '����',
    step_id bigint comment '����id',
    next_step_id bigint comment '��һ������id',
    sort bigint DEFAULT NULL COMMENT '����',
    datastatusid int DEFAULT NULL COMMENT '����״̬(0:���á�1:����)',
    update_time timestamp NULL DEFAULT NULL COMMENT '�����ʱ��',
    update_by varchar(50) DEFAULT NULL COMMENT '������Ա',
    create_time timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
    create_by varchar(50) DEFAULT NULL COMMENT '��������Ա'
) comment '������̴������ñ�';