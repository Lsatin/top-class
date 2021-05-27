CREATE TABLE `t_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键索引',
  `f_settings_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '账户设置',
  `f_details_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '账户详情',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='账户';


CREATE TABLE `t_account_details` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键索引',
  `created_by` varchar(45) DEFAULT NULL COMMENT '创建者',
  `created_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `updated_by` varchar(45) DEFAULT NULL COMMENT '更新者',
  `updated_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `version` int(11) DEFAULT NULL COMMENT '版本',
  `valid` bit(1) NOT NULL DEFAULT b'1' COMMENT '有效性',
  `firstname` varchar(45) DEFAULT NULL COMMENT '名字',
  `lastname` varchar(45) DEFAULT NULL COMMENT '姓氏',
  `username` varchar(45) DEFAULT NULL COMMENT '用户名',
  `nickname` varchar(45) DEFAULT NULL COMMENT '昵称',
  `gender` tinyint(1) NOT NULL DEFAULT '2' COMMENT '性别：0男、1女、2其他',
  `address` varchar(45) DEFAULT NULL COMMENT '地址',
  `address_1` varchar(45) DEFAULT NULL COMMENT '备用地址1',
  `address_2` varchar(45) DEFAULT NULL COMMENT '备用地址2',
  `email` varchar(45) DEFAULT NULL COMMENT '电子邮箱',
  `telephone` varchar(45) DEFAULT NULL COMMENT '联系电话',
  PRIMARY KEY (`id`),
  KEY `index_username` (`username`),
  KEY `index_gender` (`gender`),
  KEY `index_created_time` (`created_time`),
  KEY `index_create_by` (`created_by`),
  KEY `index_created_by_time` (`created_by`,`created_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='账户详情';


CREATE TABLE `t_account_settings` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键索引',
  `created_by` varchar(45) DEFAULT NULL COMMENT '创建者',
  `created_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `updated_by` varchar(45) DEFAULT NULL COMMENT '更新者',
  `updated_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `version` int(11) DEFAULT NULL COMMENT '版本',
  `valid` bit(1) NOT NULL DEFAULT b'1' COMMENT '有效性',
  `username` varchar(45) DEFAULT NULL COMMENT '用户名',
  `password` varchar(45) DEFAULT NULL COMMENT '用户密码',
  PRIMARY KEY (`id`),
  KEY `index_created_by` (`created_by`),
  KEY `index_created_time` (`created_time`),
  KEY `index_create_by_time` (`created_by`,`created_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='账户设置';


CREATE TABLE `t_school` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键索引',
  `created_by` varchar(45) DEFAULT NULL COMMENT '创建者',
  `created_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `updated_by` varchar(45) DEFAULT NULL COMMENT '更新者',
  `updated_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `version` int(11) DEFAULT NULL COMMENT '版本',
  `valid` bit(1) NOT NULL DEFAULT b'1' COMMENT '有效性',
  `name` varchar(45) DEFAULT NULL COMMENT '学校名称',
  `address` varchar(500) DEFAULT NULL COMMENT '学校地址',
  `zip_code` varchar(45) DEFAULT NULL COMMENT '邮编',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1772 DEFAULT CHARSET=utf8mb4 DELAY_KEY_WRITE=1 COMMENT='学校';
