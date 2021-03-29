CREATE TABLE `module` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `code` varchar(64) NOT NULL COMMENT '模块编码',
  `name` varchar(64) DEFAULT NULL COMMENT '模块名',
  `description` varchar(256) DEFAULT NULL COMMENT '模块说明',
  PRIMARY KEY (`id`,`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='第三方模块池';

