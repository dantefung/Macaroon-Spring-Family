CREATE TABLE `tb_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `age` int(11) NOT NULL COMMENT '年龄',
  `ctm` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
INSERT INTO `db_test`.`tb_user` (`username`, `age`, `ctm`) VALUES('张三', '18', NOW()) ;
INSERT INTO `db_test`.`tb_user` (`username`, `age`, `ctm`) VALUES('李四', '20', NOW()) ;
INSERT INTO `db_test`.`tb_user` (`username`, `age`, `ctm`) VALUES('王五', '19', NOW()) ;