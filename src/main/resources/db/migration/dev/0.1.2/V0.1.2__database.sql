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

INSERT  INTO `user`(`id`,`username`,`password`,`avatar`,`phone`,`email`,`gender`,`auth`,`status`,`create_time`,`update_time`,`is_delete`) VALUES
(1,'admin','6db978f90f22e19abb523fa38dca5a1a',NULL,'18000000000','123456@qq.com',0,'root',0,'2023-12-29 15:11:10','2023-12-29 16:26:19',0),
(2,'root','6db978f90f22e19abb523fa38dca5a1a',NULL,NULL,NULL,0,'admin',0,'2023-12-29 15:11:10','2023-12-29 15:11:10',0),
(3,'user','6db978f90f22e19abb523fa38dca5a1a',NULL,NULL,NULL,1,'user',0,'2023-12-29 15:11:10','2023-12-29 15:11:10',0),
(4,'user2','6db978f90f22e19abb523fa38dca5a1a',NULL,NULL,NULL,1,'user',0,'2023-12-31 01:46:07','2023-12-31 01:46:07',0);


DROP TABLE IF EXISTS `bill`;

CREATE TABLE `bill` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编号',
  `title` VARCHAR(200) DEFAULT NULL COMMENT '标题',
  `amount` double NOT NULL DEFAULT '0' COMMENT '金额',
  `type` int(11) DEFAULT '1' COMMENT '类型，默认1收入, 2支出',
  `data_time` VARCHAR(100) DEFAULT NULL COMMENT '账单时间',
  `tags` TEXT DEFAULT NULL COMMENT '标签',
  `detail` TEXT DEFAULT NULL COMMENT '详情',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` TINYINT(4) DEFAULT '0' COMMENT '是否被删除',
  `user_id` int(11) DEFAULT '1' COMMENT '用户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `share`(
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编号',
  `k` VARCHAR(50) NOT NULL COMMENT '唯一key',
  `v` TEXT NOT NULL COMMENT '值',
  `valid` INT DEFAULT 3 COMMENT '有效天数',
  `type` VARCHAR(20) COMMENT '类型',
  `user_id` BIGINT NOT NULL COMMENT '所属用户',
  `create_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_delete` TINYINT DEFAULT 0 COMMENT '是否被删除',
  PRIMARY KEY (`id`),
  UNIQUE INDEX (`k`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('1','title','20.59','1','2023-01-01',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('2','title','53.58','2','2023-02-15',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('3','title','32.98','2','2023-12-07',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('4','title','16.91','2','2023-11-23',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('5','title','30.54','2','2023-05-28',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('6','title','3.24','1','2023-10-13',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('7','title','42.5','2','2023-07-04',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('8','title','84.07','2','2023-04-11',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('9','title','60.68','1','2023-04-22',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('10','title','9.41','1','2023-06-13',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('11','title','65.54','2','2023-05-05',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('12','title','29.77','1','2023-06-13',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('13','title','70.94','2','2023-10-05',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('14','title','89.98','1','2023-09-28',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('15','title','98.89','1','2023-07-12',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('16','title','78.41','2','2023-11-16',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('17','title','87.36','1','2023-02-28',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('18','title','21.33','1','2023-05-23',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('19','title','52.9','1','2023-04-09',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('20','title','63.23','1','2023-01-09',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('21','title','57.21','1','2023-06-06',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('22','title','70.72','2','2023-11-08',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('23','title','82.19','2','2023-10-11',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('24','title','36.04','2','2023-01-28',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('25','title','17.24','2','2023-12-10',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('26','title','74.09','2','2023-11-21',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('27','title','28.8','2','2023-05-26',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('28','title','23.57','2','2023-03-15',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('29','title','26.06','2','2023-09-20',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('30','title','70.45','2','2023-04-13',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('31','title','95.52','2','2023-01-23',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('32','title','6.63','2','2023-10-20',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('33','title','62.51','1','2023-11-06',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('34','title','22.47','2','2023-02-01',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('35','title','77.8','2','2023-02-15',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('36','title','18.17','2','2023-07-02',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('37','title','76.11','1','2023-07-18',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('38','title','79.96','2','2023-04-26',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('39','title','31.77','2','2023-10-22',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('40','title','75.97','1','2023-02-27',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('41','title','30.36','2','2023-05-08',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('42','title','56.61','2','2023-07-27',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('43','title','15.95','2','2023-11-16',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('44','title','70.43','1','2023-08-02',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('45','title','19.26','2','2023-02-10',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('46','title','2.4','2','2023-04-19',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('47','title','99.57','2','2023-06-20',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('48','title','47.45','2','2023-04-14',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('49','title','2.81','2','2023-02-22',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('50','title','92.43','2','2023-10-05',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('51','title','1.23','1','2023-11-02',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('52','title','33.84','1','2023-12-21',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('53','title','37.12','1','2023-09-26',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('54','title','56.83','1','2023-01-05',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('55','title','29.16','1','2023-05-28',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('56','title','19.84','1','2023-01-23',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('57','title','8.3','2','2023-12-21',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('58','title','48.61','1','2023-11-27',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('59','title','47.24','1','2023-05-11',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('60','title','93.49','1','2023-09-28',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('61','title','10.6','1','2023-07-20',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('62','title','49.21','1','2023-04-12',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('63','title','98.11','1','2023-12-02',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('64','title','83.95','2','2023-04-08',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('65','title','16.46','1','2023-11-20',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('66','title','27.44','2','2023-05-27',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('67','title','84.19','2','2023-10-04',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('68','title','70.76','2','2023-03-14',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('69','title','59.75','1','2023-05-01',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('70','title','95.69','2','2023-08-02',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('71','title','38.93','1','2023-10-03',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('72','title','63.67','1','2023-12-24',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('73','title','72.63','1','2023-09-27',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('74','title','83.61','1','2023-05-03',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('75','title','71','2','2023-11-05',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('76','title','76.95','1','2023-04-13',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('77','title','81.78','2','2023-10-21',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('78','title','74.06','1','2023-05-22',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('79','title','2.52','1','2023-10-07',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('80','title','37.32','1','2023-02-17',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('81','title','13.3','2','2023-08-04',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('82','title','17.92','1','2023-12-17',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('83','title','52.69','2','2023-06-09',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('84','title','86.43','1','2023-08-13',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('85','title','81.42','1','2023-05-09',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('86','title','89.45','1','2023-06-03',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('87','title','27.49','2','2023-08-19',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('88','title','61.3','1','2023-02-25',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('89','title','14.88','1','2023-01-23',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('90','title','21.82','1','2023-05-23',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('91','title','82.43','1','2023-05-28',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('92','title','5.94','2','2023-07-21',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('93','title','0.77','2','2023-11-25',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('94','title','30.7','2','2023-03-08',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('95','title','21.84','2','2023-05-25',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('96','title','85.64','2','2023-03-01',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('97','title','62.81','2','2023-06-22',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('98','title','86.36','2','2023-06-18',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('99','title','13.11','2','2023-07-21',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
INSERT INTO `bill` (`id`, `title`, `amount`, `type`, `data_time`, `tags`, `detail`, `create_time`, `update_time`, `is_delete`, `user_id`) VALUES('100','title','90.53','2','2023-05-08',NULL,NULL,'2024-01-07 00:45:59','2024-01-07 00:45:59','0','1');
