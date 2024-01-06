DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编号',
  `username` VARCHAR(200) NOT NULL COMMENT '用户名',
  `password` VARCHAR(50) NOT NULL COMMENT '密码',
  `avatar` VARCHAR(200) DEFAULT NULL COMMENT '头像',
  `phone` VARCHAR(15) DEFAULT NULL COMMENT '手机号',
  `email` VARCHAR(30) DEFAULT NULL COMMENT '邮箱',
  `gender` TINYINT(4) DEFAULT '1' COMMENT '性别',
  `auth` VARCHAR(10) DEFAULT 'user' COMMENT '账号身份',
  `status` TINYINT(4) DEFAULT '0' COMMENT '账号状态',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` TINYINT(4) DEFAULT '0' COMMENT '是否被删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE` (`username`)
) ENGINE=INNODB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

/*Data for the table `user` */

INSERT  INTO `user`(`id`,`username`,`password`,`avatar`,`phone`,`email`,`gender`,`auth`,`status`,`create_time`,`update_time`,`is_delete`) VALUES (1,'admin','3e452803f5235cc9058b28fcf73bba0b','/static/file/7b76cac79a5c492c87c7c0f2ebabf4de.png','18000000000','123456@qq.com',0,'root',0,'2023-12-29 15:11:10','2023-12-29 16:26:19',0),(2,'root','3e452803f5235cc9058b28fcf73bba0b',NULL,NULL,NULL,0,'admin',0,'2023-12-29 15:11:10','2023-12-29 15:11:10',0),(3,'user','3e452803f5235cc9058b28fcf73bba0b',NULL,NULL,NULL,1,'user',0,'2023-12-29 15:11:10','2023-12-29 15:11:10',0),(4,'user2','3e452803f5235cc9058b28fcf73bba0b',NULL,NULL,NULL,1,'user',0,'2023-12-31 01:46:07','2023-12-31 01:46:07',0);
