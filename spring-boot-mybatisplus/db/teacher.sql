/*
Navicat MySQL Data Transfer

Source Server         : local_mysql
Source Server Version : 50726
Source Host           : localhost:3306
Source Database       : teacher

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2020-04-27 17:31:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for organization
-- ----------------------------
DROP TABLE IF EXISTS `organization`;
CREATE TABLE `organization` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(64) NOT NULL COMMENT '组织名',
  `address` varchar(100) DEFAULT NULL COMMENT '地址',
  `code` varchar(64) NOT NULL COMMENT '编号',
  `icon` varchar(32) DEFAULT NULL COMMENT '图标',
  `pid` bigint(19) DEFAULT NULL COMMENT '父级主键',
  `seq` tinyint(2) NOT NULL DEFAULT '0' COMMENT '排序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='组织机构';

-- ----------------------------
-- Records of organization
-- ----------------------------
INSERT INTO `organization` VALUES ('1', '总经办', '王家桥', '01', 'fi-social-windows', null, '0', '2014-02-19 01:00:00');
INSERT INTO `organization` VALUES ('3', '技术部', '', '02', 'fi-social-github', null, '1', '2015-10-01 13:10:42');
INSERT INTO `organization` VALUES ('5', '产品部', '', '03', 'fi-social-apple', null, '2', '2015-12-06 12:15:30');
INSERT INTO `organization` VALUES ('6', '测试组', '', '04', 'fi-social-snapchat', '3', '0', '2015-12-06 13:12:18');

-- ----------------------------
-- Table structure for resource
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) NOT NULL COMMENT '资源名称',
  `url` varchar(100) DEFAULT NULL COMMENT '资源路径',
  `open_mode` varchar(32) DEFAULT NULL COMMENT '打开方式 ajax,iframe',
  `description` varchar(255) DEFAULT NULL COMMENT '资源介绍',
  `icon` varchar(32) DEFAULT NULL COMMENT '资源图标',
  `pid` bigint(19) DEFAULT NULL COMMENT '父级资源id',
  `seq` tinyint(2) NOT NULL DEFAULT '0' COMMENT '排序',
  `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '状态',
  `opened` tinyint(2) NOT NULL DEFAULT '1' COMMENT '打开状态',
  `resource_type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '资源类别',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=264 DEFAULT CHARSET=utf8 COMMENT='资源';

-- ----------------------------
-- Records of resource
-- ----------------------------
INSERT INTO `resource` VALUES ('1', '权限管理', '', null, '系统管理', 'fi-folder', null, '0', '0', '1', '0', '2014-02-19 01:00:00');
INSERT INTO `resource` VALUES ('11', '资源管理', '/resource/manager', 'ajax', '资源管理', 'fi-database', '1', '1', '0', '1', '0', '2014-02-19 01:00:00');
INSERT INTO `resource` VALUES ('12', '角色管理', '/role/manager', 'ajax', '角色管理', 'fi-torso-business', '1', '2', '0', '1', '0', '2014-02-19 01:00:00');
INSERT INTO `resource` VALUES ('13', '用户管理', '/user/manager', 'ajax', '用户管理', 'fi-torsos-all', '1', '3', '0', '1', '0', '2014-02-19 01:00:00');
INSERT INTO `resource` VALUES ('14', '部门管理', '/organization/manager', 'ajax', '部门管理', 'fi-results-demographics', '1', '4', '0', '1', '0', '2014-02-19 01:00:00');
INSERT INTO `resource` VALUES ('111', '列表', '/resource/treeGrid', 'ajax', '资源列表', 'fi-list', '11', '0', '0', '1', '1', '2014-02-19 01:00:00');
INSERT INTO `resource` VALUES ('112', '添加', '/resource/add', 'ajax', '资源添加', 'fi-page-add', '11', '0', '0', '1', '1', '2014-02-19 01:00:00');
INSERT INTO `resource` VALUES ('113', '编辑', '/resource/edit', 'ajax', '资源编辑', 'fi-page-edit', '11', '0', '0', '1', '1', '2014-02-19 01:00:00');
INSERT INTO `resource` VALUES ('114', '删除', '/resource/delete', 'ajax', '资源删除', 'fi-page-delete', '11', '0', '0', '1', '1', '2014-02-19 01:00:00');
INSERT INTO `resource` VALUES ('121', '列表', '/role/dataGrid', 'ajax', '角色列表', 'fi-list', '12', '0', '0', '1', '1', '2014-02-19 01:00:00');
INSERT INTO `resource` VALUES ('122', '添加', '/role/add', 'ajax', '角色添加', 'fi-page-add', '12', '0', '0', '1', '1', '2014-02-19 01:00:00');
INSERT INTO `resource` VALUES ('123', '编辑', '/role/edit', 'ajax', '角色编辑', 'fi-page-edit', '12', '0', '0', '1', '1', '2014-02-19 01:00:00');
INSERT INTO `resource` VALUES ('124', '删除', '/role/delete', 'ajax', '角色删除', 'fi-page-delete', '12', '0', '0', '1', '1', '2014-02-19 01:00:00');
INSERT INTO `resource` VALUES ('125', '授权', '/role/grant', 'ajax', '角色授权', 'fi-check', '12', '0', '0', '1', '1', '2014-02-19 01:00:00');
INSERT INTO `resource` VALUES ('131', '列表', '/user/dataGrid', 'ajax', '用户列表', 'fi-list', '13', '0', '0', '1', '1', '2014-02-19 01:00:00');
INSERT INTO `resource` VALUES ('132', '添加', '/user/add', 'ajax', '用户添加', 'fi-page-add', '13', '0', '0', '1', '1', '2014-02-19 01:00:00');
INSERT INTO `resource` VALUES ('133', '编辑', '/user/edit', 'ajax', '用户编辑', 'fi-page-edit', '13', '0', '0', '1', '1', '2014-02-19 01:00:00');
INSERT INTO `resource` VALUES ('134', '删除', '/user/delete', 'ajax', '用户删除', 'fi-page-delete', '13', '0', '0', '1', '1', '2014-02-19 01:00:00');
INSERT INTO `resource` VALUES ('141', '列表', '/organization/treeGrid', 'ajax', '用户列表', 'fi-list', '14', '0', '0', '1', '1', '2014-02-19 01:00:00');
INSERT INTO `resource` VALUES ('142', '添加', '/organization/add', 'ajax', '部门添加', 'fi-page-add', '14', '0', '0', '1', '1', '2014-02-19 01:00:00');
INSERT INTO `resource` VALUES ('143', '编辑', '/organization/edit', 'ajax', '部门编辑', 'fi-page-edit', '14', '0', '0', '1', '1', '2014-02-19 01:00:00');
INSERT INTO `resource` VALUES ('144', '删除', '/organization/delete', 'ajax', '部门删除', 'fi-page-delete', '14', '0', '0', '1', '1', '2014-02-19 01:00:00');
INSERT INTO `resource` VALUES ('221', '日志监控', '', null, null, 'fi-folder', null, '3', '0', '0', '0', '2015-12-01 11:44:20');
INSERT INTO `resource` VALUES ('222', '外聘教师管理', '', '', null, 'fi-folder', null, '2', '0', '1', '0', '2015-12-06 12:40:42');
INSERT INTO `resource` VALUES ('223', '教师信息', '/tTeacher/manager', 'ajax', null, 'fi-home', '222', '0', '0', '1', '0', '2015-12-06 12:42:42');
INSERT INTO `resource` VALUES ('224', '列表', '/tTeacher/dataGrid', 'ajax', null, 'fi-list', '223', '0', '0', '1', '1', '2015-12-06 12:45:28');
INSERT INTO `resource` VALUES ('226', '修改密码', '/user/editPwdPage', 'ajax', null, 'fi-unlock', null, '4', '0', '1', '1', '2015-12-07 20:23:06');
INSERT INTO `resource` VALUES ('227', '登录日志', '/sysLog/manager', 'ajax', null, 'fi-info', '221', '0', '0', '1', '0', '2016-09-30 22:10:53');
INSERT INTO `resource` VALUES ('228', 'Druid监控', '/druid', 'iframe', null, 'fi-monitor', '221', '0', '0', '1', '0', '2016-09-30 22:12:50');
INSERT INTO `resource` VALUES ('229', '系统图标', '/icons.html', 'ajax', null, 'fi-photo', '221', '0', '0', '1', '0', '2016-12-24 15:53:47');
INSERT INTO `resource` VALUES ('230', '文章管理', '', 'ajax', null, 'fi-page-multiple', null, '1', '0', '0', '0', '2016-12-24 15:53:47');
INSERT INTO `resource` VALUES ('231', '新建文章', '/article/create', 'ajax', null, 'fi-page-edit', '230', '0', '0', '1', '0', '2016-12-24 15:53:47');
INSERT INTO `resource` VALUES ('232', '添加', '/tTeacher/add', 'ajax', null, 'fi-page-add', '223', '1', '0', '1', '1', '2017-12-27 09:40:18');
INSERT INTO `resource` VALUES ('233', '编辑', '/tTeacher/edit', 'ajax', null, 'fi-page-edit', '223', '2', '0', '1', '1', '2017-12-27 09:40:51');
INSERT INTO `resource` VALUES ('234', '删除', '/tTeacher/delete', 'ajax', null, 'fi-page-delete', '223', '3', '0', '1', '1', '2017-12-27 09:41:38');
INSERT INTO `resource` VALUES ('235', '信息统计', '/tTeacher/statisticView', 'ajax', null, 'fi-home', '222', '0', '0', '1', '0', '2017-12-27 12:18:50');
INSERT INTO `resource` VALUES ('237', '学校信息维护', '', '', null, 'fi-folder', null, '0', '0', '1', '0', '2017-12-27 12:28:55');
INSERT INTO `resource` VALUES ('238', '列表', '/tCourse/dataGrid', 'ajax', null, 'fi-list', '242', '0', '0', '1', '1', '2017-12-27 12:29:50');
INSERT INTO `resource` VALUES ('239', '添加', '/tCourse/add', 'ajax', null, 'fi-page-add', '242', '1', '0', '1', '1', '2017-12-27 12:30:28');
INSERT INTO `resource` VALUES ('240', '编辑', '/tCourse/edit', 'ajax', null, 'fi-page-edit', '242', '2', '0', '1', '1', '2017-12-27 12:31:10');
INSERT INTO `resource` VALUES ('241', '删除', '/tCourse/delete', 'ajax', null, 'fi-page-delete', '242', '4', '0', '1', '1', '2017-12-27 12:31:39');
INSERT INTO `resource` VALUES ('242', '课程管理', '/tCourse/manager', null, null, 'fi-home', '237', '0', '0', '1', '0', '2017-12-27 14:26:28');
INSERT INTO `resource` VALUES ('243', '系部管理', '/tDept/manager', null, null, 'fi-home', '237', '0', '0', '1', '0', '2017-12-27 15:55:20');
INSERT INTO `resource` VALUES ('244', '列表', '/tDept/dataGrid', 'ajax', null, 'fi-list', '243', '1', '0', '1', '1', '2017-12-27 15:55:59');
INSERT INTO `resource` VALUES ('245', '添加', '/tDept/add', 'ajax', null, 'fi-page-add', '243', '2', '0', '1', '1', '2017-12-27 15:57:04');
INSERT INTO `resource` VALUES ('246', '编辑', '/tDept/edit', 'ajax', null, 'fi-page-edit', '243', '3', '0', '1', '1', '2017-12-27 15:57:54');
INSERT INTO `resource` VALUES ('247', '删除', '/tDept/delete', 'ajax', null, 'fi-page-delete', '243', '4', '0', '1', '1', '2017-12-27 15:58:30');
INSERT INTO `resource` VALUES ('248', '列表管理', '/sysListKey/manager', null, null, 'fi-folder', '237', '0', '0', '1', '0', '2017-12-27 17:03:28');
INSERT INTO `resource` VALUES ('249', '列表', '/sysListKey/dataGrid', 'ajax', null, 'fi-list', '248', '1', '0', '1', '1', '2017-12-27 17:06:20');
INSERT INTO `resource` VALUES ('250', '添加', '/sysListKey/add', 'ajax', null, 'fi-page-add', '248', '2', '0', '1', '1', '2017-12-27 17:07:01');
INSERT INTO `resource` VALUES ('251', '编辑', '/sysListKey/edit', 'ajax', null, 'fi-page-edit', '248', '2', '0', '1', '1', '2017-12-27 17:07:39');
INSERT INTO `resource` VALUES ('252', '删除', '/sysListKey/delete', 'ajax', null, 'fi-page-delete', '248', '3', '0', '1', '1', '2017-12-27 17:08:14');
INSERT INTO `resource` VALUES ('253', '代课管理', '/tTeacherCourse/manager', null, null, 'fi-home', '222', '1', '0', '1', '0', '2017-12-27 18:22:03');
INSERT INTO `resource` VALUES ('254', '列表', '/tTeacherCourse/dataGrid', 'ajax', null, 'fi-list', '253', '1', '0', '1', '1', '2017-12-27 18:22:59');
INSERT INTO `resource` VALUES ('255', '添加', '/tTeacherCourse/add', 'ajax', null, 'fi-page-add', '253', '2', '0', '1', '1', '2017-12-27 18:23:29');
INSERT INTO `resource` VALUES ('256', '编辑', '/tTeacherCourse/edit', 'ajax', null, 'fi-page-edit', '253', '3', '0', '1', '1', '2017-12-27 18:24:03');
INSERT INTO `resource` VALUES ('257', '删除', '/tTeacherCourse/delete', 'ajax', null, 'fi-page-delete', '253', '4', '0', '1', '1', '2017-12-27 18:25:08');
INSERT INTO `resource` VALUES ('258', '工资管理', '', null, null, 'fi-folder', null, '0', '0', '1', '0', '2017-12-27 21:23:54');
INSERT INTO `resource` VALUES ('259', '工资记录', '/tSalary/manager', null, null, 'fi-list', '258', '0', '0', '1', '0', '2017-12-27 21:29:34');
INSERT INTO `resource` VALUES ('260', '工资结算', '/tSalary/addPage', 'ajax', null, 'fi-page-add', '258', '1', '0', '1', '0', '2017-12-27 21:59:21');
INSERT INTO `resource` VALUES ('261', '结算', '/tSalary/calc', 'ajax', null, 'fi-page-add', '260', '0', '0', '1', '1', '2017-12-27 23:43:41');
INSERT INTO `resource` VALUES ('262', '报表管理', '', null, null, 'fi-folder', null, '0', '0', '1', '0', '2017-12-28 00:50:16');
INSERT INTO `resource` VALUES ('263', '教师职称统计', '/report/teacherReportView', 'ajax', null, 'fi-home', '262', '0', '0', '1', '0', '2017-12-28 00:51:48');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(64) NOT NULL COMMENT '角色名',
  `seq` tinyint(2) NOT NULL DEFAULT '0' COMMENT '排序号',
  `description` varchar(255) DEFAULT NULL COMMENT '简介',
  `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'admin', '0', '超级管理员', '0');
INSERT INTO `role` VALUES ('2', 'de', '0', '技术部经理', '0');
INSERT INTO `role` VALUES ('7', 'pm', '0', '产品部经理', '0');
INSERT INTO `role` VALUES ('8', 'test', '0', '测试账户', '0');

-- ----------------------------
-- Table structure for role_resource
-- ----------------------------
DROP TABLE IF EXISTS `role_resource`;
CREATE TABLE `role_resource` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_id` bigint(19) NOT NULL COMMENT '角色id',
  `resource_id` bigint(19) NOT NULL COMMENT '资源id',
  PRIMARY KEY (`id`),
  KEY `idx_role_resource_ids` (`role_id`,`resource_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=988 DEFAULT CHARSET=utf8 COMMENT='角色资源';

-- ----------------------------
-- Records of role_resource
-- ----------------------------
INSERT INTO `role_resource` VALUES ('930', '1', '1');
INSERT INTO `role_resource` VALUES ('931', '1', '11');
INSERT INTO `role_resource` VALUES ('936', '1', '12');
INSERT INTO `role_resource` VALUES ('942', '1', '13');
INSERT INTO `role_resource` VALUES ('947', '1', '14');
INSERT INTO `role_resource` VALUES ('932', '1', '111');
INSERT INTO `role_resource` VALUES ('933', '1', '112');
INSERT INTO `role_resource` VALUES ('934', '1', '113');
INSERT INTO `role_resource` VALUES ('935', '1', '114');
INSERT INTO `role_resource` VALUES ('937', '1', '121');
INSERT INTO `role_resource` VALUES ('938', '1', '122');
INSERT INTO `role_resource` VALUES ('939', '1', '123');
INSERT INTO `role_resource` VALUES ('940', '1', '124');
INSERT INTO `role_resource` VALUES ('941', '1', '125');
INSERT INTO `role_resource` VALUES ('943', '1', '131');
INSERT INTO `role_resource` VALUES ('944', '1', '132');
INSERT INTO `role_resource` VALUES ('945', '1', '133');
INSERT INTO `role_resource` VALUES ('946', '1', '134');
INSERT INTO `role_resource` VALUES ('948', '1', '141');
INSERT INTO `role_resource` VALUES ('949', '1', '142');
INSERT INTO `role_resource` VALUES ('950', '1', '143');
INSERT INTO `role_resource` VALUES ('951', '1', '144');
INSERT INTO `role_resource` VALUES ('984', '1', '221');
INSERT INTO `role_resource` VALUES ('972', '1', '222');
INSERT INTO `role_resource` VALUES ('973', '1', '223');
INSERT INTO `role_resource` VALUES ('974', '1', '224');
INSERT INTO `role_resource` VALUES ('987', '1', '226');
INSERT INTO `role_resource` VALUES ('985', '1', '227');
INSERT INTO `role_resource` VALUES ('986', '1', '228');
INSERT INTO `role_resource` VALUES ('975', '1', '232');
INSERT INTO `role_resource` VALUES ('976', '1', '233');
INSERT INTO `role_resource` VALUES ('977', '1', '234');
INSERT INTO `role_resource` VALUES ('978', '1', '235');
INSERT INTO `role_resource` VALUES ('956', '1', '237');
INSERT INTO `role_resource` VALUES ('958', '1', '238');
INSERT INTO `role_resource` VALUES ('959', '1', '239');
INSERT INTO `role_resource` VALUES ('960', '1', '240');
INSERT INTO `role_resource` VALUES ('961', '1', '241');
INSERT INTO `role_resource` VALUES ('957', '1', '242');
INSERT INTO `role_resource` VALUES ('962', '1', '243');
INSERT INTO `role_resource` VALUES ('963', '1', '244');
INSERT INTO `role_resource` VALUES ('964', '1', '245');
INSERT INTO `role_resource` VALUES ('965', '1', '246');
INSERT INTO `role_resource` VALUES ('966', '1', '247');
INSERT INTO `role_resource` VALUES ('967', '1', '248');
INSERT INTO `role_resource` VALUES ('968', '1', '249');
INSERT INTO `role_resource` VALUES ('969', '1', '250');
INSERT INTO `role_resource` VALUES ('970', '1', '251');
INSERT INTO `role_resource` VALUES ('971', '1', '252');
INSERT INTO `role_resource` VALUES ('979', '1', '253');
INSERT INTO `role_resource` VALUES ('980', '1', '254');
INSERT INTO `role_resource` VALUES ('981', '1', '255');
INSERT INTO `role_resource` VALUES ('982', '1', '256');
INSERT INTO `role_resource` VALUES ('983', '1', '257');
INSERT INTO `role_resource` VALUES ('952', '1', '258');
INSERT INTO `role_resource` VALUES ('953', '1', '259');
INSERT INTO `role_resource` VALUES ('954', '1', '260');
INSERT INTO `role_resource` VALUES ('955', '1', '261');
INSERT INTO `role_resource` VALUES ('437', '2', '1');
INSERT INTO `role_resource` VALUES ('438', '2', '13');
INSERT INTO `role_resource` VALUES ('439', '2', '131');
INSERT INTO `role_resource` VALUES ('440', '2', '132');
INSERT INTO `role_resource` VALUES ('441', '2', '133');
INSERT INTO `role_resource` VALUES ('445', '2', '221');
INSERT INTO `role_resource` VALUES ('442', '2', '222');
INSERT INTO `role_resource` VALUES ('443', '2', '223');
INSERT INTO `role_resource` VALUES ('444', '2', '224');
INSERT INTO `role_resource` VALUES ('446', '2', '227');
INSERT INTO `role_resource` VALUES ('447', '2', '228');
INSERT INTO `role_resource` VALUES ('158', '3', '1');
INSERT INTO `role_resource` VALUES ('159', '3', '11');
INSERT INTO `role_resource` VALUES ('164', '3', '12');
INSERT INTO `role_resource` VALUES ('170', '3', '13');
INSERT INTO `role_resource` VALUES ('175', '3', '14');
INSERT INTO `role_resource` VALUES ('160', '3', '111');
INSERT INTO `role_resource` VALUES ('161', '3', '112');
INSERT INTO `role_resource` VALUES ('162', '3', '113');
INSERT INTO `role_resource` VALUES ('163', '3', '114');
INSERT INTO `role_resource` VALUES ('165', '3', '121');
INSERT INTO `role_resource` VALUES ('166', '3', '122');
INSERT INTO `role_resource` VALUES ('167', '3', '123');
INSERT INTO `role_resource` VALUES ('168', '3', '124');
INSERT INTO `role_resource` VALUES ('169', '3', '125');
INSERT INTO `role_resource` VALUES ('171', '3', '131');
INSERT INTO `role_resource` VALUES ('172', '3', '132');
INSERT INTO `role_resource` VALUES ('173', '3', '133');
INSERT INTO `role_resource` VALUES ('174', '3', '134');
INSERT INTO `role_resource` VALUES ('176', '3', '141');
INSERT INTO `role_resource` VALUES ('177', '3', '142');
INSERT INTO `role_resource` VALUES ('178', '3', '143');
INSERT INTO `role_resource` VALUES ('179', '3', '144');
INSERT INTO `role_resource` VALUES ('359', '7', '1');
INSERT INTO `role_resource` VALUES ('360', '7', '14');
INSERT INTO `role_resource` VALUES ('361', '7', '141');
INSERT INTO `role_resource` VALUES ('362', '7', '142');
INSERT INTO `role_resource` VALUES ('363', '7', '143');
INSERT INTO `role_resource` VALUES ('367', '7', '221');
INSERT INTO `role_resource` VALUES ('364', '7', '222');
INSERT INTO `role_resource` VALUES ('365', '7', '223');
INSERT INTO `role_resource` VALUES ('366', '7', '224');
INSERT INTO `role_resource` VALUES ('368', '7', '226');
INSERT INTO `role_resource` VALUES ('448', '8', '1');
INSERT INTO `role_resource` VALUES ('449', '8', '11');
INSERT INTO `role_resource` VALUES ('451', '8', '12');
INSERT INTO `role_resource` VALUES ('453', '8', '13');
INSERT INTO `role_resource` VALUES ('455', '8', '14');
INSERT INTO `role_resource` VALUES ('450', '8', '111');
INSERT INTO `role_resource` VALUES ('452', '8', '121');
INSERT INTO `role_resource` VALUES ('454', '8', '131');
INSERT INTO `role_resource` VALUES ('456', '8', '141');
INSERT INTO `role_resource` VALUES ('460', '8', '221');
INSERT INTO `role_resource` VALUES ('457', '8', '222');
INSERT INTO `role_resource` VALUES ('458', '8', '223');
INSERT INTO `role_resource` VALUES ('459', '8', '224');
INSERT INTO `role_resource` VALUES ('461', '8', '227');
INSERT INTO `role_resource` VALUES ('462', '8', '228');
INSERT INTO `role_resource` VALUES ('478', '8', '229');
INSERT INTO `role_resource` VALUES ('479', '8', '230');
INSERT INTO `role_resource` VALUES ('480', '8', '231');

-- ----------------------------
-- Table structure for sys_list_key
-- ----------------------------
DROP TABLE IF EXISTS `sys_list_key`;
CREATE TABLE `sys_list_key` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '列表id',
  `key_code` varchar(100) DEFAULT NULL COMMENT '键编码',
  `memo` varchar(300) DEFAULT NULL COMMENT '备注',
  `default_value` varchar(100) DEFAULT NULL COMMENT '默认值',
  `create_time` date DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  `delete_flag` int(11) DEFAULT NULL,
  `group_code` varchar(255) DEFAULT NULL,
  `group_name` varchar(255) DEFAULT NULL,
  `standby1` varchar(255) DEFAULT NULL,
  `standby2` varchar(255) DEFAULT NULL,
  `standby3` varchar(255) DEFAULT NULL,
  `key_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='下拉列表';

-- ----------------------------
-- Records of sys_list_key
-- ----------------------------
INSERT INTO `sys_list_key` VALUES ('1', '10', '初级代课金100元', '100', '2017-12-27', '2017-12-27', '0', 'dk.level', '代课金组别', null, null, null, '初级');
INSERT INTO `sys_list_key` VALUES ('2', '20', '中级代课金200元', '200', '2017-12-27', '2017-12-27', '0', 'dk.level', '代课金组别', null, null, null, '中级');
INSERT INTO `sys_list_key` VALUES ('3', '30', '高级代课金300元', '300', '2017-12-27', '2017-12-27', '0', 'dk.level', '代课金组别', null, null, null, '高级');
INSERT INTO `sys_list_key` VALUES ('4', '100', '测试银级', '100', '2017-12-27', '2017-12-27', '0', 'test.level', '等级测试', null, null, null, '测试银级');
INSERT INTO `sys_list_key` VALUES ('5', '200', '测试金级', '200', '2017-12-27', '2017-12-27', '1', 'test.level', '等级测试', null, null, null, '测试金级');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `login_name` varchar(255) DEFAULT NULL COMMENT '登陆名',
  `role_name` varchar(255) DEFAULT NULL COMMENT '角色名',
  `opt_content` varchar(1024) DEFAULT NULL COMMENT '内容',
  `client_ip` varchar(255) DEFAULT NULL COMMENT '客户端ip',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=714 DEFAULT CHARSET=utf8 COMMENT='系统日志';

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES ('391', 'test', 'test', '[类名]:com.wangzhixuan.controller.CommonsController,[方法]:ueditor,[参数]:action=config&noCache=1514276571855&', '0:0:0:0:0:0:0:1', '2017-12-26 16:22:52');
INSERT INTO `sys_log` VALUES ('392', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:editPage,[参数]:id=222&_=1514338238118&', '0:0:0:0:0:0:0:1', '2017-12-27 09:31:24');
INSERT INTO `sys_log` VALUES ('393', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:addPage,[参数]:', null, '2017-12-27 09:31:59');
INSERT INTO `sys_log` VALUES ('394', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:editPage,[参数]:id=222&_=1514338238120&', '0:0:0:0:0:0:0:1', '2017-12-27 09:32:16');
INSERT INTO `sys_log` VALUES ('395', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:edit,[参数]:id=222&name=外聘教师管理&resourceType=0&url=&openMode=&icon=fi-folder&seq=2&status=0&opened=1&pid=&', '0:0:0:0:0:0:0:1', '2017-12-27 09:32:54');
INSERT INTO `sys_log` VALUES ('396', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:editPage,[参数]:id=223&_=1514338238121&', '0:0:0:0:0:0:0:1', '2017-12-27 09:32:57');
INSERT INTO `sys_log` VALUES ('397', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:edit,[参数]:id=223&name=信息维护&resourceType=0&url=https://www.dreamlu.net&openMode=ajax&icon=fi-home&seq=0&status=0&opened=1&pid=222&', '0:0:0:0:0:0:0:1', '2017-12-27 09:34:10');
INSERT INTO `sys_log` VALUES ('398', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:editPage,[参数]:id=223&_=1514338238122&', '0:0:0:0:0:0:0:1', '2017-12-27 09:34:12');
INSERT INTO `sys_log` VALUES ('399', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:edit,[参数]:id=223&name=信息维护&resourceType=0&url=/tTeacher/manager&openMode=ajax&icon=fi-home&seq=0&status=0&opened=1&pid=222&', '0:0:0:0:0:0:0:1', '2017-12-27 09:34:24');
INSERT INTO `sys_log` VALUES ('400', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:editPage,[参数]:id=224&_=1514338238123&', '0:0:0:0:0:0:0:1', '2017-12-27 09:34:27');
INSERT INTO `sys_log` VALUES ('401', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:edit,[参数]:id=224&name=jfinal视频&resourceType=0&url=/tTeacher/dataGrid&openMode=ajax&icon=fi-list&seq=0&status=0&opened=1&pid=223&', '0:0:0:0:0:0:0:1', '2017-12-27 09:35:03');
INSERT INTO `sys_log` VALUES ('402', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:editPage,[参数]:id=224&_=1514338238124&', '0:0:0:0:0:0:0:1', '2017-12-27 09:35:06');
INSERT INTO `sys_log` VALUES ('403', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:edit,[参数]:id=224&name=列表&resourceType=0&url=/tTeacher/dataGrid&openMode=ajax&icon=fi-list&seq=0&status=0&opened=1&pid=223&', '0:0:0:0:0:0:0:1', '2017-12-27 09:35:15');
INSERT INTO `sys_log` VALUES ('404', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:addPage,[参数]:', null, '2017-12-27 09:38:53');
INSERT INTO `sys_log` VALUES ('405', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:add,[参数]:name=添加&resourceType=1&url=/tTeacher/add&openMode=ajax&icon=fi-page-add&seq=1&status=0&opened=1&pid=223&', '0:0:0:0:0:0:0:1', '2017-12-27 09:40:18');
INSERT INTO `sys_log` VALUES ('406', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:addPage,[参数]:', null, '2017-12-27 09:40:21');
INSERT INTO `sys_log` VALUES ('407', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:add,[参数]:name=编辑&resourceType=0&url=/tTeacher/edit&openMode=ajax&icon=fi-page-edit&seq=2&status=0&opened=1&pid=223&', '0:0:0:0:0:0:0:1', '2017-12-27 09:40:51');
INSERT INTO `sys_log` VALUES ('408', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:addPage,[参数]:', null, '2017-12-27 09:40:54');
INSERT INTO `sys_log` VALUES ('409', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:editPage,[参数]:id=233&_=1514338238128&', '0:0:0:0:0:0:0:1', '2017-12-27 09:41:02');
INSERT INTO `sys_log` VALUES ('410', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:edit,[参数]:id=233&name=编辑&resourceType=0&url=/tTeacher/edit&openMode=ajax&icon=fi-page-edit&seq=2&status=0&opened=1&pid=223&', '0:0:0:0:0:0:0:1', '2017-12-27 09:41:05');
INSERT INTO `sys_log` VALUES ('411', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:addPage,[参数]:', null, '2017-12-27 09:41:06');
INSERT INTO `sys_log` VALUES ('412', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:add,[参数]:name=删除&resourceType=1&url=/tTeacher/delete&openMode=ajax&icon=fi-page-delete&seq=3&status=0&opened=1&pid=223&', '0:0:0:0:0:0:0:1', '2017-12-27 09:41:38');
INSERT INTO `sys_log` VALUES ('413', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:editPage,[参数]:id=233&_=1514338904767&', '0:0:0:0:0:0:0:1', '2017-12-27 09:42:04');
INSERT INTO `sys_log` VALUES ('414', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:edit,[参数]:id=233&name=编辑&resourceType=1&url=/tTeacher/edit&openMode=ajax&icon=fi-page-edit&seq=2&status=0&opened=1&pid=223&', '0:0:0:0:0:0:0:1', '2017-12-27 09:42:08');
INSERT INTO `sys_log` VALUES ('415', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:editPage,[参数]:id=224&_=1514338904768&', '0:0:0:0:0:0:0:1', '2017-12-27 09:42:09');
INSERT INTO `sys_log` VALUES ('416', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:edit,[参数]:id=224&name=列表&resourceType=1&url=/tTeacher/dataGrid&openMode=ajax&icon=fi-list&seq=0&status=0&opened=1&pid=223&', '0:0:0:0:0:0:0:1', '2017-12-27 09:42:13');
INSERT INTO `sys_log` VALUES ('417', 'admin', 'admin', '[类名]:com.mgrsys.controller.RoleController,[方法]:grantPage,[参数]:id=1&_=1514348143611&', '0:0:0:0:0:0:0:1', '2017-12-27 12:16:04');
INSERT INTO `sys_log` VALUES ('418', 'admin', 'admin', '[类名]:com.mgrsys.controller.RoleController,[方法]:grant,[参数]:id=1&resourceIds=1,11,111,112,113,114,12,121,122,123,124,125,13,131,132,133,134,14,141,142,143,144,222,223,224,232,233,234,221,227,228&', '0:0:0:0:0:0:0:1', '2017-12-27 12:16:11');
INSERT INTO `sys_log` VALUES ('419', 'admin', 'admin', '[类名]:com.mgrsys.controller.LoginController,[方法]:logout,[参数]:', null, '2017-12-27 12:16:41');
INSERT INTO `sys_log` VALUES ('420', 'admin', 'admin', '[类名]:com.mgrsys.controller.TTeacherController,[方法]:addPage,[参数]:', null, '2017-12-27 12:16:55');
INSERT INTO `sys_log` VALUES ('421', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:addPage,[参数]:', null, '2017-12-27 12:17:16');
INSERT INTO `sys_log` VALUES ('422', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:add,[参数]:name=信息统计&resourceType=0&url=&openMode=无(用于上层菜单)&icon=fi-folder&seq=0&status=0&opened=1&pid=222&', '0:0:0:0:0:0:0:1', '2017-12-27 12:18:50');
INSERT INTO `sys_log` VALUES ('423', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:addPage,[参数]:', null, '2017-12-27 12:18:56');
INSERT INTO `sys_log` VALUES ('424', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:add,[参数]:name=列表&resourceType=1&url=/tTeacher/statistics&openMode=ajax&icon=fi-list&seq=1&status=0&opened=1&pid=235&', '0:0:0:0:0:0:0:1', '2017-12-27 12:20:51');
INSERT INTO `sys_log` VALUES ('425', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:editPage,[参数]:id=235&_=1514348208567&', '0:0:0:0:0:0:0:1', '2017-12-27 12:21:34');
INSERT INTO `sys_log` VALUES ('426', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:edit,[参数]:id=235&name=信息统计&resourceType=0&url=&openMode=iframe&icon=fi-home&seq=0&status=0&opened=1&pid=222&', '0:0:0:0:0:0:0:1', '2017-12-27 12:21:44');
INSERT INTO `sys_log` VALUES ('427', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:editPage,[参数]:id=235&_=1514348208568&', '0:0:0:0:0:0:0:1', '2017-12-27 12:21:53');
INSERT INTO `sys_log` VALUES ('428', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:edit,[参数]:id=235&name=信息统计&resourceType=0&url=www.baidu.com&openMode=iframe&icon=fi-home&seq=0&status=0&opened=1&pid=222&', '0:0:0:0:0:0:0:1', '2017-12-27 12:22:00');
INSERT INTO `sys_log` VALUES ('429', 'admin', 'admin', '[类名]:com.mgrsys.controller.RoleController,[方法]:grantPage,[参数]:id=1&_=1514348208570&', '0:0:0:0:0:0:0:1', '2017-12-27 12:22:05');
INSERT INTO `sys_log` VALUES ('430', 'admin', 'admin', '[类名]:com.mgrsys.controller.RoleController,[方法]:grant,[参数]:id=1&resourceIds=1,11,111,112,113,114,12,121,122,123,124,125,13,131,132,133,134,14,141,142,143,144,222,223,224,232,233,234,235,236,221,227,228&', '0:0:0:0:0:0:0:1', '2017-12-27 12:22:12');
INSERT INTO `sys_log` VALUES ('431', 'admin', 'admin', '[类名]:com.mgrsys.controller.LoginController,[方法]:logout,[参数]:', null, '2017-12-27 12:22:16');
INSERT INTO `sys_log` VALUES ('432', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:delete,[参数]:id=236&', '0:0:0:0:0:0:0:1', '2017-12-27 12:24:09');
INSERT INTO `sys_log` VALUES ('433', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:addPage,[参数]:', null, '2017-12-27 12:28:30');
INSERT INTO `sys_log` VALUES ('434', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:add,[参数]:name=课程管理&resourceType=0&url=&openMode=无(用于上层菜单)&icon=fi-folder&seq=0&status=0&opened=1&pid=&', '0:0:0:0:0:0:0:1', '2017-12-27 12:28:55');
INSERT INTO `sys_log` VALUES ('435', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:addPage,[参数]:', null, '2017-12-27 12:29:03');
INSERT INTO `sys_log` VALUES ('436', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:add,[参数]:name=列表&resourceType=1&url=/tCourse/dataGrid&openMode=ajax&icon=fi-list&seq=0&status=0&opened=1&pid=237&', '0:0:0:0:0:0:0:1', '2017-12-27 12:29:50');
INSERT INTO `sys_log` VALUES ('437', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:addPage,[参数]:', null, '2017-12-27 12:29:53');
INSERT INTO `sys_log` VALUES ('438', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:add,[参数]:name=添加&resourceType=1&url=/tCourse/add&openMode=ajax&icon=fi-page-add&seq=1&status=0&opened=1&pid=237&', '0:0:0:0:0:0:0:1', '2017-12-27 12:30:28');
INSERT INTO `sys_log` VALUES ('439', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:addPage,[参数]:', null, '2017-12-27 12:30:30');
INSERT INTO `sys_log` VALUES ('440', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:add,[参数]:name=编辑&resourceType=1&url=/tCourse/edit&openMode=ajax&icon=fi-page-edit&seq=2&status=0&opened=1&pid=237&', '0:0:0:0:0:0:0:1', '2017-12-27 12:31:10');
INSERT INTO `sys_log` VALUES ('441', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:addPage,[参数]:', null, '2017-12-27 12:31:12');
INSERT INTO `sys_log` VALUES ('442', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:add,[参数]:name=删除&resourceType=1&url=/tCourse/delete&openMode=ajax&icon=fi-page-delete&seq=4&status=0&opened=1&pid=237&', '0:0:0:0:0:0:0:1', '2017-12-27 12:31:38');
INSERT INTO `sys_log` VALUES ('443', 'admin', 'admin', '[类名]:com.mgrsys.controller.RoleController,[方法]:grantPage,[参数]:id=1&_=1514348542898&', '0:0:0:0:0:0:0:1', '2017-12-27 14:20:27');
INSERT INTO `sys_log` VALUES ('444', 'admin', 'admin', '[类名]:com.mgrsys.controller.RoleController,[方法]:grant,[参数]:id=1&resourceIds=1,11,111,112,113,114,12,121,122,123,124,125,13,131,132,133,134,14,141,142,143,144,237,238,239,240,241,222,223,224,232,233,234,235,221,227,228&', '0:0:0:0:0:0:0:1', '2017-12-27 14:20:34');
INSERT INTO `sys_log` VALUES ('445', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:editPage,[参数]:id=237&_=1514355880343&', '0:0:0:0:0:0:0:1', '2017-12-27 14:25:10');
INSERT INTO `sys_log` VALUES ('446', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:edit,[参数]:id=237&name=学校信息维护&resourceType=0&url=&openMode=&icon=fi-folder&seq=0&status=0&opened=1&pid=&', '0:0:0:0:0:0:0:1', '2017-12-27 14:25:28');
INSERT INTO `sys_log` VALUES ('447', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:addPage,[参数]:', null, '2017-12-27 14:25:31');
INSERT INTO `sys_log` VALUES ('448', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:add,[参数]:name=课程管理&resourceType=0&url=/tCourse/manager&openMode=ajax&icon=fi-home&seq=0&status=0&opened=1&pid=237&', '0:0:0:0:0:0:0:1', '2017-12-27 14:26:28');
INSERT INTO `sys_log` VALUES ('449', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:editPage,[参数]:id=238&_=1514355880345&', '0:0:0:0:0:0:0:1', '2017-12-27 14:26:35');
INSERT INTO `sys_log` VALUES ('450', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:edit,[参数]:id=238&name=列表&resourceType=1&url=/tCourse/dataGrid&openMode=ajax&icon=fi-list&seq=0&status=0&opened=1&pid=242&', '0:0:0:0:0:0:0:1', '2017-12-27 14:26:38');
INSERT INTO `sys_log` VALUES ('451', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:editPage,[参数]:id=239&_=1514355880346&', '0:0:0:0:0:0:0:1', '2017-12-27 14:26:43');
INSERT INTO `sys_log` VALUES ('452', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:edit,[参数]:id=239&name=添加&resourceType=1&url=/tCourse/add&openMode=ajax&icon=fi-page-add&seq=1&status=0&opened=1&pid=242&', '0:0:0:0:0:0:0:1', '2017-12-27 14:26:48');
INSERT INTO `sys_log` VALUES ('453', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:editPage,[参数]:id=240&_=1514355880347&', '0:0:0:0:0:0:0:1', '2017-12-27 14:26:50');
INSERT INTO `sys_log` VALUES ('454', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:edit,[参数]:id=240&name=编辑&resourceType=1&url=/tCourse/edit&openMode=ajax&icon=fi-page-edit&seq=2&status=0&opened=1&pid=242&', '0:0:0:0:0:0:0:1', '2017-12-27 14:26:53');
INSERT INTO `sys_log` VALUES ('455', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:editPage,[参数]:id=241&_=1514355880348&', '0:0:0:0:0:0:0:1', '2017-12-27 14:26:56');
INSERT INTO `sys_log` VALUES ('456', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:edit,[参数]:id=241&name=删除&resourceType=1&url=/tCourse/delete&openMode=ajax&icon=fi-page-delete&seq=4&status=0&opened=1&pid=242&', '0:0:0:0:0:0:0:1', '2017-12-27 14:27:03');
INSERT INTO `sys_log` VALUES ('457', 'admin', 'admin', '[类名]:com.mgrsys.controller.RoleController,[方法]:grantPage,[参数]:id=1&_=1514355880350&', '0:0:0:0:0:0:0:1', '2017-12-27 14:27:09');
INSERT INTO `sys_log` VALUES ('458', 'admin', 'admin', '[类名]:com.mgrsys.controller.RoleController,[方法]:grant,[参数]:id=1&resourceIds=1,11,111,112,113,114,12,121,122,123,124,125,13,131,132,133,134,14,141,142,143,144,237,238,239,240,241,222,223,224,232,233,234,235,221,227,228&', '0:0:0:0:0:0:0:1', '2017-12-27 14:27:14');
INSERT INTO `sys_log` VALUES ('459', 'admin', 'admin', '[类名]:com.mgrsys.controller.LoginController,[方法]:logout,[参数]:', null, '2017-12-27 14:27:22');
INSERT INTO `sys_log` VALUES ('460', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:addPage,[参数]:', null, '2017-12-27 14:38:00');
INSERT INTO `sys_log` VALUES ('461', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:addPage,[参数]:', null, '2017-12-27 14:38:19');
INSERT INTO `sys_log` VALUES ('462', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:addPage,[参数]:', null, '2017-12-27 14:42:42');
INSERT INTO `sys_log` VALUES ('463', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:addPage,[参数]:', null, '2017-12-27 14:45:09');
INSERT INTO `sys_log` VALUES ('464', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:addPage,[参数]:', null, '2017-12-27 14:46:29');
INSERT INTO `sys_log` VALUES ('465', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:addPage,[参数]:', null, '2017-12-27 14:50:49');
INSERT INTO `sys_log` VALUES ('466', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:addPage,[参数]:', null, '2017-12-27 14:51:05');
INSERT INTO `sys_log` VALUES ('467', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:addPage,[参数]:', null, '2017-12-27 14:51:14');
INSERT INTO `sys_log` VALUES ('468', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:addPage,[参数]:', null, '2017-12-27 14:51:39');
INSERT INTO `sys_log` VALUES ('469', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:addPage,[参数]:', null, '2017-12-27 14:51:52');
INSERT INTO `sys_log` VALUES ('470', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:addPage,[参数]:', null, '2017-12-27 14:51:58');
INSERT INTO `sys_log` VALUES ('471', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:add,[参数]:name=高等数学&credit=10&tHour=5&dkLevel=10&', '0:0:0:0:0:0:0:1', '2017-12-27 14:52:40');
INSERT INTO `sys_log` VALUES ('472', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:addPage,[参数]:', null, '2017-12-27 15:06:45');
INSERT INTO `sys_log` VALUES ('473', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:editPage,[参数]:id=1&_=1514358269579&', '0:0:0:0:0:0:0:1', '2017-12-27 15:08:35');
INSERT INTO `sys_log` VALUES ('474', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:editPage,[参数]:id=1&_=1514358269581&', '0:0:0:0:0:0:0:1', '2017-12-27 15:08:46');
INSERT INTO `sys_log` VALUES ('475', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:editPage,[参数]:id=1&_=1514358269582&', '0:0:0:0:0:0:0:1', '2017-12-27 15:09:17');
INSERT INTO `sys_log` VALUES ('476', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:editPage,[参数]:id=1&_=1514358269584&', '0:0:0:0:0:0:0:1', '2017-12-27 15:09:23');
INSERT INTO `sys_log` VALUES ('477', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:editPage,[参数]:id=1&_=1514358269585&', '0:0:0:0:0:0:0:1', '2017-12-27 15:09:37');
INSERT INTO `sys_log` VALUES ('478', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:editPage,[参数]:id=1&_=1514358269587&', '0:0:0:0:0:0:0:1', '2017-12-27 15:10:17');
INSERT INTO `sys_log` VALUES ('479', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:editPage,[参数]:id=1&_=1514358269589&', '0:0:0:0:0:0:0:1', '2017-12-27 15:10:38');
INSERT INTO `sys_log` VALUES ('480', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:editPage,[参数]:id=1&_=1514358269592&', '0:0:0:0:0:0:0:1', '2017-12-27 15:12:28');
INSERT INTO `sys_log` VALUES ('481', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:editPage,[参数]:id=1&_=1514358269593&', '0:0:0:0:0:0:0:1', '2017-12-27 15:13:44');
INSERT INTO `sys_log` VALUES ('482', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:addPage,[参数]:', null, '2017-12-27 15:14:33');
INSERT INTO `sys_log` VALUES ('483', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:addPage,[参数]:', null, '2017-12-27 15:15:22');
INSERT INTO `sys_log` VALUES ('484', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:addPage,[参数]:', null, '2017-12-27 15:18:01');
INSERT INTO `sys_log` VALUES ('485', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:editPage,[参数]:id=1&_=1514359092160&', '0:0:0:0:0:0:0:1', '2017-12-27 15:18:18');
INSERT INTO `sys_log` VALUES ('486', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:addPage,[参数]:', null, '2017-12-27 15:19:31');
INSERT INTO `sys_log` VALUES ('487', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:addPage,[参数]:', null, '2017-12-27 15:20:16');
INSERT INTO `sys_log` VALUES ('488', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:add,[参数]:name=线性代数&credit=2&tHour=20&dkLevel=20&', '0:0:0:0:0:0:0:1', '2017-12-27 15:23:20');
INSERT INTO `sys_log` VALUES ('489', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:editPage,[参数]:id=2&_=1514359111986&', '0:0:0:0:0:0:0:1', '2017-12-27 15:23:24');
INSERT INTO `sys_log` VALUES ('490', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:edit,[参数]:name=线性代数&credit=2&tHour=20&dkLevel=30&', '0:0:0:0:0:0:0:1', '2017-12-27 15:23:28');
INSERT INTO `sys_log` VALUES ('491', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:editPage,[参数]:id=2&_=1514359111988&', '0:0:0:0:0:0:0:1', '2017-12-27 15:23:43');
INSERT INTO `sys_log` VALUES ('492', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:edit,[参数]:name=线性代数&credit=2&tHour=20&dkLevel=30&', '0:0:0:0:0:0:0:1', '2017-12-27 15:23:47');
INSERT INTO `sys_log` VALUES ('493', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:edit,[参数]:name=线性代数&credit=2&tHour=20&dkLevel=30&', '0:0:0:0:0:0:0:1', '2017-12-27 15:24:06');
INSERT INTO `sys_log` VALUES ('494', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:edit,[参数]:name=线性代数&credit=2&tHour=20&dkLevel=30&', '0:0:0:0:0:0:0:1', '2017-12-27 15:24:10');
INSERT INTO `sys_log` VALUES ('495', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:edit,[参数]:name=线性代数&credit=2&tHour=20&dkLevel=30&', '0:0:0:0:0:0:0:1', '2017-12-27 15:24:21');
INSERT INTO `sys_log` VALUES ('496', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:edit,[参数]:name=线性代数&credit=2&tHour=20&dkLevel=30&', '0:0:0:0:0:0:0:1', '2017-12-27 15:24:56');
INSERT INTO `sys_log` VALUES ('497', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:edit,[参数]:name=线性代数&credit=2&tHour=20&dkLevel=30&', '0:0:0:0:0:0:0:1', '2017-12-27 15:25:28');
INSERT INTO `sys_log` VALUES ('498', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:editPage,[参数]:id=2&_=1514359111990&', '0:0:0:0:0:0:0:1', '2017-12-27 15:27:13');
INSERT INTO `sys_log` VALUES ('499', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:edit,[参数]:name=线性代数&credit=2&tHour=20&dkLevel=30&', '0:0:0:0:0:0:0:1', '2017-12-27 15:27:19');
INSERT INTO `sys_log` VALUES ('500', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:editPage,[参数]:id=2&_=1514359111992&', '0:0:0:0:0:0:0:1', '2017-12-27 15:29:31');
INSERT INTO `sys_log` VALUES ('501', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:editPage,[参数]:id=2&_=1514360771750&', '0:0:0:0:0:0:0:1', '2017-12-27 15:48:26');
INSERT INTO `sys_log` VALUES ('502', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:edit,[参数]:name=线性代数&credit=2&tHour=20&dkLevel=30&createTime=2017-12-27&deleteFlag=0&', '0:0:0:0:0:0:0:1', '2017-12-27 15:48:35');
INSERT INTO `sys_log` VALUES ('503', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:editPage,[参数]:id=1&_=1514360771752&', '0:0:0:0:0:0:0:1', '2017-12-27 15:51:15');
INSERT INTO `sys_log` VALUES ('504', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:edit,[参数]:name=高等数学&credit=10&tHour=5&dkLevel=10&createTime=2017-12-27&deleteFlag=0&', '0:0:0:0:0:0:0:1', '2017-12-27 15:51:17');
INSERT INTO `sys_log` VALUES ('505', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:edit,[参数]:name=高等数学&credit=10&tHour=5&dkLevel=10&createTime=2017-12-27&deleteFlag=0&', '0:0:0:0:0:0:0:1', '2017-12-27 15:51:30');
INSERT INTO `sys_log` VALUES ('506', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:edit,[参数]:name=高等数学&credit=10&tHour=5&dkLevel=10&createTime=2017-12-27&deleteFlag=0&', '0:0:0:0:0:0:0:1', '2017-12-27 15:51:38');
INSERT INTO `sys_log` VALUES ('507', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:editPage,[参数]:id=2&_=1514360771753&', '0:0:0:0:0:0:0:1', '2017-12-27 15:52:39');
INSERT INTO `sys_log` VALUES ('508', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:edit,[参数]:name=线性代数&credit=2&tHour=20&dkLevel=30&id=2&createTime=2017-12-27&deleteFlag=0&', '0:0:0:0:0:0:0:1', '2017-12-27 15:52:45');
INSERT INTO `sys_log` VALUES ('509', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:addPage,[参数]:', null, '2017-12-27 15:53:58');
INSERT INTO `sys_log` VALUES ('510', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:add,[参数]:name=系部管理&resourceType=0&url=/tDept/manager&openMode=ajax&icon=fi-home&seq=0&status=0&opened=1&pid=237&', '0:0:0:0:0:0:0:1', '2017-12-27 15:55:20');
INSERT INTO `sys_log` VALUES ('511', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:addPage,[参数]:', null, '2017-12-27 15:55:25');
INSERT INTO `sys_log` VALUES ('512', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:add,[参数]:name=列表&resourceType=0&url=/tDept/list&openMode=ajax&icon=fi-list&seq=1&status=0&opened=1&pid=243&', '0:0:0:0:0:0:0:1', '2017-12-27 15:55:59');
INSERT INTO `sys_log` VALUES ('513', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:editPage,[参数]:id=244&_=1514360771757&', '0:0:0:0:0:0:0:1', '2017-12-27 15:56:10');
INSERT INTO `sys_log` VALUES ('514', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:edit,[参数]:id=244&name=列表&resourceType=0&url=/tDept/dataGrid&openMode=ajax&icon=fi-list&seq=1&status=0&opened=1&pid=243&', '0:0:0:0:0:0:0:1', '2017-12-27 15:56:24');
INSERT INTO `sys_log` VALUES ('515', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:addPage,[参数]:', null, '2017-12-27 15:56:29');
INSERT INTO `sys_log` VALUES ('516', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:add,[参数]:name=添加&resourceType=1&url=/tDept/add&openMode=ajax&icon=fi-page-add&seq=2&status=0&opened=1&pid=243&', '0:0:0:0:0:0:0:1', '2017-12-27 15:57:04');
INSERT INTO `sys_log` VALUES ('517', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:editPage,[参数]:id=244&_=1514360771759&', '0:0:0:0:0:0:0:1', '2017-12-27 15:57:08');
INSERT INTO `sys_log` VALUES ('518', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:edit,[参数]:id=244&name=列表&resourceType=1&url=/tDept/dataGrid&openMode=ajax&icon=fi-list&seq=1&status=0&opened=1&pid=243&', '0:0:0:0:0:0:0:1', '2017-12-27 15:57:10');
INSERT INTO `sys_log` VALUES ('519', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:addPage,[参数]:', null, '2017-12-27 15:57:13');
INSERT INTO `sys_log` VALUES ('520', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:add,[参数]:name=编辑&resourceType=1&url=/tDept/edit&openMode=ajax&icon=fi-page-delete&seq=3&status=0&opened=1&pid=243&', '0:0:0:0:0:0:0:1', '2017-12-27 15:57:54');
INSERT INTO `sys_log` VALUES ('521', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:addPage,[参数]:', null, '2017-12-27 15:57:56');
INSERT INTO `sys_log` VALUES ('522', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:add,[参数]:name=删除&resourceType=1&url=/tDept/delete&openMode=ajax&icon=fi-page-delete&seq=4&status=0&opened=1&pid=243&', '0:0:0:0:0:0:0:1', '2017-12-27 15:58:30');
INSERT INTO `sys_log` VALUES ('523', 'admin', 'admin', '[类名]:com.mgrsys.controller.RoleController,[方法]:grantPage,[参数]:id=1&_=1514360771763&', '0:0:0:0:0:0:0:1', '2017-12-27 15:58:37');
INSERT INTO `sys_log` VALUES ('524', 'admin', 'admin', '[类名]:com.mgrsys.controller.RoleController,[方法]:grant,[参数]:id=1&resourceIds=1,11,111,112,113,114,12,121,122,123,124,125,13,131,132,133,134,14,141,142,143,144,237,238,239,240,241,243,244,245,246,247,222,223,224,232,233,234,235,221,227,228&', '0:0:0:0:0:0:0:1', '2017-12-27 15:58:47');
INSERT INTO `sys_log` VALUES ('525', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:editPage,[参数]:id=246&_=1514360771764&', '0:0:0:0:0:0:0:1', '2017-12-27 15:58:54');
INSERT INTO `sys_log` VALUES ('526', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:edit,[参数]:id=246&name=编辑&resourceType=1&url=/tDept/edit&openMode=ajax&icon=fi-page-edit&seq=3&status=0&opened=1&pid=243&', '0:0:0:0:0:0:0:1', '2017-12-27 15:59:05');
INSERT INTO `sys_log` VALUES ('527', 'admin', 'admin', '[类名]:com.mgrsys.controller.LoginController,[方法]:logout,[参数]:', null, '2017-12-27 15:59:10');
INSERT INTO `sys_log` VALUES ('528', 'admin', 'admin', '[类名]:com.mgrsys.controller.TDeptController,[方法]:addPage,[参数]:', null, '2017-12-27 15:59:26');
INSERT INTO `sys_log` VALUES ('529', 'admin', 'admin', '[类名]:com.mgrsys.controller.TDeptController,[方法]:addPage,[参数]:', null, '2017-12-27 16:04:12');
INSERT INTO `sys_log` VALUES ('530', 'admin', 'admin', '[类名]:com.mgrsys.controller.TDeptController,[方法]:add,[参数]:deptName=理学院&', '0:0:0:0:0:0:0:1', '2017-12-27 16:04:21');
INSERT INTO `sys_log` VALUES ('531', 'admin', 'admin', '[类名]:com.mgrsys.controller.TDeptController,[方法]:addPage,[参数]:', null, '2017-12-27 16:04:25');
INSERT INTO `sys_log` VALUES ('532', 'admin', 'admin', '[类名]:com.mgrsys.controller.TDeptController,[方法]:add,[参数]:deptName=商学院&', '0:0:0:0:0:0:0:1', '2017-12-27 16:04:31');
INSERT INTO `sys_log` VALUES ('533', 'admin', 'admin', '[类名]:com.mgrsys.controller.TDeptController,[方法]:addPage,[参数]:', null, '2017-12-27 16:04:33');
INSERT INTO `sys_log` VALUES ('534', 'admin', 'admin', '[类名]:com.mgrsys.controller.TDeptController,[方法]:add,[参数]:deptName=计算机学院&', '0:0:0:0:0:0:0:1', '2017-12-27 16:04:39');
INSERT INTO `sys_log` VALUES ('535', 'admin', 'admin', '[类名]:com.mgrsys.controller.TDeptController,[方法]:editPage,[参数]:id=3&_=1514361556176&', '0:0:0:0:0:0:0:1', '2017-12-27 16:04:44');
INSERT INTO `sys_log` VALUES ('536', 'admin', 'admin', '[类名]:com.mgrsys.controller.TDeptController,[方法]:edit,[参数]:id=3&name=计算机学院2&', '0:0:0:0:0:0:0:1', '2017-12-27 16:04:46');
INSERT INTO `sys_log` VALUES ('537', 'admin', 'admin', '[类名]:com.mgrsys.controller.TDeptController,[方法]:delete,[参数]:id=3&', '0:0:0:0:0:0:0:1', '2017-12-27 16:04:51');
INSERT INTO `sys_log` VALUES ('538', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:addPage,[参数]:', null, '2017-12-27 16:18:55');
INSERT INTO `sys_log` VALUES ('539', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:add,[参数]:name=列表管理&resourceType=0&url=/sysListKey/manager&openMode=无(用于上层菜单)&icon=fi-folder&seq=0&status=0&opened=1&pid=237&', '0:0:0:0:0:0:0:1', '2017-12-27 17:03:28');
INSERT INTO `sys_log` VALUES ('540', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:addPage,[参数]:', null, '2017-12-27 17:05:39');
INSERT INTO `sys_log` VALUES ('541', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:add,[参数]:name=列表&resourceType=1&url=/sysListKey/dataGrid&openMode=ajax&icon=fi-list&seq=1&status=0&opened=1&pid=248&', '0:0:0:0:0:0:0:1', '2017-12-27 17:06:20');
INSERT INTO `sys_log` VALUES ('542', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:addPage,[参数]:', null, '2017-12-27 17:06:26');
INSERT INTO `sys_log` VALUES ('543', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:add,[参数]:name=添加&resourceType=1&url=/sysListKey/add&openMode=ajax&icon=fi-page-add&seq=2&status=0&opened=1&pid=248&', '0:0:0:0:0:0:0:1', '2017-12-27 17:07:01');
INSERT INTO `sys_log` VALUES ('544', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:addPage,[参数]:', null, '2017-12-27 17:07:02');
INSERT INTO `sys_log` VALUES ('545', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:add,[参数]:name=编辑&resourceType=1&url=/sysListKey/edit&openMode=ajax&icon=fi-page-edit&seq=2&status=0&opened=1&pid=248&', '0:0:0:0:0:0:0:1', '2017-12-27 17:07:39');
INSERT INTO `sys_log` VALUES ('546', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:addPage,[参数]:', null, '2017-12-27 17:07:41');
INSERT INTO `sys_log` VALUES ('547', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:add,[参数]:name=删除&resourceType=1&url=/sysListKey/delete&openMode=ajax&icon=fi-page-delete&seq=3&status=0&opened=1&pid=248&', '0:0:0:0:0:0:0:1', '2017-12-27 17:08:14');
INSERT INTO `sys_log` VALUES ('548', 'admin', 'admin', '[类名]:com.mgrsys.controller.RoleController,[方法]:grantPage,[参数]:id=1&_=1514361556185&', '0:0:0:0:0:0:0:1', '2017-12-27 17:08:43');
INSERT INTO `sys_log` VALUES ('549', 'admin', 'admin', '[类名]:com.mgrsys.controller.RoleController,[方法]:grant,[参数]:id=1&resourceIds=1,11,111,112,113,114,12,121,122,123,124,125,13,131,132,133,134,14,141,142,143,144,237,238,239,240,241,243,244,245,246,247,248,249,250,251,252,222,223,224,232,233,234,235,221,227,228&', '0:0:0:0:0:0:0:1', '2017-12-27 17:08:50');
INSERT INTO `sys_log` VALUES ('550', 'admin', 'admin', '[类名]:com.mgrsys.controller.LoginController,[方法]:logout,[参数]:', null, '2017-12-27 17:08:53');
INSERT INTO `sys_log` VALUES ('551', 'admin', 'admin', '[类名]:com.mgrsys.controller.TCourseController,[方法]:editPage,[参数]:id=1&_=1514366443567&', '0:0:0:0:0:0:0:1', '2017-12-27 17:21:13');
INSERT INTO `sys_log` VALUES ('552', 'admin', 'admin', '[类名]:com.mgrsys.controller.SysListKeyController,[方法]:editPage,[参数]:id=1&_=1514366443569&', '0:0:0:0:0:0:0:1', '2017-12-27 17:26:59');
INSERT INTO `sys_log` VALUES ('553', 'admin', 'admin', '[类名]:com.mgrsys.controller.SysListKeyController,[方法]:editPage,[参数]:id=1&_=1514366443571&', '0:0:0:0:0:0:0:1', '2017-12-27 17:27:17');
INSERT INTO `sys_log` VALUES ('554', 'admin', 'admin', '[类名]:com.mgrsys.controller.SysListKeyController,[方法]:edit,[参数]:keyCode=10&keyName=初级&memo=初级代课金100元&defaultValue=100&groupCode=dk.level&groupName=代课金组别1&id=1&', '0:0:0:0:0:0:0:1', '2017-12-27 17:27:31');
INSERT INTO `sys_log` VALUES ('555', 'admin', 'admin', '[类名]:com.mgrsys.controller.SysListKeyController,[方法]:editPage,[参数]:id=1&_=1514366443572&', '0:0:0:0:0:0:0:1', '2017-12-27 17:27:35');
INSERT INTO `sys_log` VALUES ('556', 'admin', 'admin', '[类名]:com.mgrsys.controller.SysListKeyController,[方法]:edit,[参数]:keyCode=10&keyName=初级&memo=初级代课金100元&defaultValue=100&groupCode=dk.level&groupName=代课金组别&id=1&', '0:0:0:0:0:0:0:1', '2017-12-27 17:27:38');
INSERT INTO `sys_log` VALUES ('557', 'admin', 'admin', '[类名]:com.mgrsys.controller.SysListKeyController,[方法]:addPage,[参数]:', null, '2017-12-27 17:27:49');
INSERT INTO `sys_log` VALUES ('558', 'admin', 'admin', '[类名]:com.mgrsys.controller.SysListKeyController,[方法]:add,[参数]:keyCode=100&keyName=测试银级&memo=测试银级&defaultValue=100&groupCode=test.level&groupName=等级测试&', '0:0:0:0:0:0:0:1', '2017-12-27 17:29:26');
INSERT INTO `sys_log` VALUES ('559', 'admin', 'admin', '[类名]:com.mgrsys.controller.SysListKeyController,[方法]:addPage,[参数]:', null, '2017-12-27 17:29:30');
INSERT INTO `sys_log` VALUES ('560', 'admin', 'admin', '[类名]:com.mgrsys.controller.SysListKeyController,[方法]:add,[参数]:keyCode=200&keyName=测试金级&memo=测试金级&defaultValue=200&groupCode=test.level&groupName=等级测试&', '0:0:0:0:0:0:0:1', '2017-12-27 17:30:14');
INSERT INTO `sys_log` VALUES ('561', 'admin', 'admin', '[类名]:com.mgrsys.controller.SysListKeyController,[方法]:delete,[参数]:id=5&', '0:0:0:0:0:0:0:1', '2017-12-27 17:30:25');
INSERT INTO `sys_log` VALUES ('562', 'admin', 'admin', '[类名]:com.mgrsys.controller.UserController,[方法]:editPage,[参数]:id=13&_=1514368399822&', '0:0:0:0:0:0:0:1', '2017-12-27 18:02:16');
INSERT INTO `sys_log` VALUES ('563', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:addPage,[参数]:', null, '2017-12-27 18:04:10');
INSERT INTO `sys_log` VALUES ('564', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.ResourceController,[鏂规硶]:add,[鍙傛暟]:name=代课管理&resourceType=0&url=/tTeacherCourse/manager&openMode=ajax&icon=fi-home&seq=1&status=0&opened=1&pid=222&', '0:0:0:0:0:0:0:1', '2017-12-27 18:22:03');
INSERT INTO `sys_log` VALUES ('565', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.ResourceController,[鏂规硶]:addPage,[鍙傛暟]:', null, '2017-12-27 18:22:19');
INSERT INTO `sys_log` VALUES ('566', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.ResourceController,[鏂规硶]:add,[鍙傛暟]:name=列表&resourceType=1&url=/tTeacherCourse/dataGrid&openMode=ajax&icon=fi-list&seq=1&status=0&opened=1&pid=253&', '0:0:0:0:0:0:0:1', '2017-12-27 18:22:59');
INSERT INTO `sys_log` VALUES ('567', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.ResourceController,[鏂规硶]:addPage,[鍙傛暟]:', null, '2017-12-27 18:23:00');
INSERT INTO `sys_log` VALUES ('568', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.ResourceController,[鏂规硶]:add,[鍙傛暟]:name=添加&resourceType=1&url=/tTeacherCourse/add&openMode=ajax&icon=fi-page-add&seq=2&status=0&opened=1&pid=253&', '0:0:0:0:0:0:0:1', '2017-12-27 18:23:29');
INSERT INTO `sys_log` VALUES ('569', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.ResourceController,[鏂规硶]:addPage,[鍙傛暟]:', null, '2017-12-27 18:23:31');
INSERT INTO `sys_log` VALUES ('570', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.ResourceController,[鏂规硶]:add,[鍙傛暟]:name=编辑&resourceType=1&url=/tTeacherCourse/edit&openMode=ajax&icon=fi-page-edit&seq=3&status=0&opened=1&pid=253&', '0:0:0:0:0:0:0:1', '2017-12-27 18:24:03');
INSERT INTO `sys_log` VALUES ('571', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.ResourceController,[鏂规硶]:addPage,[鍙傛暟]:', null, '2017-12-27 18:24:04');
INSERT INTO `sys_log` VALUES ('572', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.ResourceController,[鏂规硶]:add,[鍙傛暟]:name=删除&resourceType=1&url=/tTeacherCourse/delete&openMode=ajax&icon=fi-page-delete&seq=4&status=0&opened=1&pid=253&', '0:0:0:0:0:0:0:1', '2017-12-27 18:25:08');
INSERT INTO `sys_log` VALUES ('573', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.RoleController,[鏂规硶]:grantPage,[鍙傛暟]:id=1&_=1514368399829&', '0:0:0:0:0:0:0:1', '2017-12-27 18:25:12');
INSERT INTO `sys_log` VALUES ('574', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.RoleController,[鏂规硶]:grant,[鍙傛暟]:id=1&resourceIds=1,11,111,112,113,114,12,121,122,123,124,125,13,131,132,133,134,14,141,142,143,144,237,238,239,240,241,243,244,245,246,247,248,249,250,251,252,222,223,224,232,233,234,235,253,254,255,256,257,221,227,228,226&', '0:0:0:0:0:0:0:1', '2017-12-27 18:25:22');
INSERT INTO `sys_log` VALUES ('575', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.LoginController,[鏂规硶]:logout,[鍙傛暟]:', null, '2017-12-27 18:25:26');
INSERT INTO `sys_log` VALUES ('576', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.TTeacherCourseController,[鏂规硶]:addPage,[鍙傛暟]:', null, '2017-12-27 18:31:44');
INSERT INTO `sys_log` VALUES ('577', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.ResourceController,[鏂规硶]:editPage,[鍙傛暟]:id=223&_=1514370332691&', '0:0:0:0:0:0:0:1', '2017-12-27 18:32:16');
INSERT INTO `sys_log` VALUES ('578', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.ResourceController,[鏂规硶]:edit,[鍙傛暟]:id=223&name=教师信息&resourceType=0&url=/tTeacher/manager&openMode=ajax&icon=fi-home&seq=0&status=0&opened=1&pid=222&', '0:0:0:0:0:0:0:1', '2017-12-27 18:32:30');
INSERT INTO `sys_log` VALUES ('579', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.ResourceController,[鏂规硶]:editPage,[鍙傛暟]:id=223&_=1514370332693&', '0:0:0:0:0:0:0:1', '2017-12-27 18:32:53');
INSERT INTO `sys_log` VALUES ('580', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.ResourceController,[鏂规硶]:edit,[鍙傛暟]:id=223&name=教师信息&resourceType=0&url=/tTeacher/manager&openMode=ajax&icon=fi-home&seq=0&status=0&opened=1&pid=222&', '0:0:0:0:0:0:0:1', '2017-12-27 18:33:05');
INSERT INTO `sys_log` VALUES ('581', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.RoleController,[鏂规硶]:grantPage,[鍙傛暟]:id=1&_=1514370332695&', '0:0:0:0:0:0:0:1', '2017-12-27 18:33:11');
INSERT INTO `sys_log` VALUES ('582', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.RoleController,[鏂规硶]:grant,[鍙傛暟]:id=1&resourceIds=1,11,111,112,113,114,12,121,122,123,124,125,13,131,132,133,134,14,141,142,143,144,237,242,238,239,240,241,243,244,245,246,247,248,249,250,251,252,222,223,224,232,233,234,235,253,254,255,256,257,221,227,228,226&', '0:0:0:0:0:0:0:1', '2017-12-27 18:33:24');
INSERT INTO `sys_log` VALUES ('583', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.LoginController,[鏂规硶]:logout,[鍙傛暟]:', null, '2017-12-27 18:33:26');
INSERT INTO `sys_log` VALUES ('584', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.TTeacherController,[鏂规硶]:addPage,[鍙傛暟]:', null, '2017-12-27 18:33:37');
INSERT INTO `sys_log` VALUES ('585', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.TTeacherController,[鏂规硶]:add,[鍙傛暟]:name=张三&gender=1&empNo=SN13111222&idNo=445231245654x&birthday=2017-12-21&degree=10&deptId=1&mobile=13211115555&qq=12314556&email=123@qq.com&address=北京清华园&', '0:0:0:0:0:0:0:1', '2017-12-27 18:34:59');
INSERT INTO `sys_log` VALUES ('586', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.TTeacherController,[鏂规硶]:editPage,[鍙傛暟]:id=1&_=1514370812878&', '0:0:0:0:0:0:0:1', '2017-12-27 18:35:08');
INSERT INTO `sys_log` VALUES ('587', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.TTeacherController,[鏂规硶]:edit,[鍙傛暟]:name=张三&gender=1&empNo=SN13111222&idNo=445231245654x&birthday=2017-12-21&degree=10&deptId=1&mobile=13211115555&qq=12314556&email=123@qq.com&address=北京清华园&id=1&', '0:0:0:0:0:0:0:1', '2017-12-27 18:35:11');
INSERT INTO `sys_log` VALUES ('588', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.TTeacherController,[鏂规硶]:editPage,[鍙傛暟]:id=1&_=1514370812880&', '0:0:0:0:0:0:0:1', '2017-12-27 18:50:20');
INSERT INTO `sys_log` VALUES ('589', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.TTeacherController,[鏂规硶]:editPage,[鍙傛暟]:id=1&_=1514370812881&', '0:0:0:0:0:0:0:1', '2017-12-27 18:52:31');
INSERT INTO `sys_log` VALUES ('590', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.TTeacherController,[鏂规硶]:editPage,[鍙傛暟]:id=1&_=1514370812882&', '0:0:0:0:0:0:0:1', '2017-12-27 18:53:08');
INSERT INTO `sys_log` VALUES ('591', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.TTeacherController,[鏂规硶]:edit,[鍙傛暟]:name=张三&gender=1&empNo=SN13111222&idNo=445231245654x&birthday=2017-12-21&degree=10&deptId=1&mobile=教授&qq=12314556&email=123@qq.com&address=北京清华园&id=1&', '0:0:0:0:0:0:0:1', '2017-12-27 18:53:17');
INSERT INTO `sys_log` VALUES ('592', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.TTeacherController,[鏂规硶]:edit,[鍙傛暟]:name=张三&gender=1&empNo=SN13111222&idNo=445231245654x&birthday=2017-12-21&degree=10&deptId=1&mobile=教授&qq=12314556&email=123@qq.com&address=北京清华园&id=1&', '0:0:0:0:0:0:0:1', '2017-12-27 18:53:26');
INSERT INTO `sys_log` VALUES ('593', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.TTeacherController,[鏂规硶]:editPage,[鍙傛暟]:id=1&_=1514370812883&', '0:0:0:0:0:0:0:1', '2017-12-27 18:53:59');
INSERT INTO `sys_log` VALUES ('594', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.TTeacherController,[鏂规硶]:edit,[鍙傛暟]:name=张三&gender=1&empNo=SN13111222&idNo=445231245654x&birthday=2017-12-21&degree=10&deptId=1&title=教授&mobile=13211115555&qq=12314556&email=123@qq.com&address=北京清华园&id=1&', '0:0:0:0:0:0:0:1', '2017-12-27 18:54:06');
INSERT INTO `sys_log` VALUES ('595', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.TTeacherController,[鏂规硶]:addPage,[鍙傛暟]:', null, '2017-12-27 18:56:08');
INSERT INTO `sys_log` VALUES ('596', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.TTeacherController,[鏂规硶]:addPage,[鍙傛暟]:', null, '2017-12-27 18:56:46');
INSERT INTO `sys_log` VALUES ('597', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.TTeacherController,[鏂规硶]:addPage,[鍙傛暟]:', null, '2017-12-27 18:58:08');
INSERT INTO `sys_log` VALUES ('598', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.TTeacherController,[鏂规硶]:addPage,[鍙傛暟]:', null, '2017-12-27 19:05:22');
INSERT INTO `sys_log` VALUES ('599', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.TTeacherController,[鏂规硶]:addPage,[鍙傛暟]:', null, '2017-12-27 19:06:04');
INSERT INTO `sys_log` VALUES ('600', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.TTeacherController,[鏂规硶]:addPage,[鍙傛暟]:', null, '2017-12-27 19:06:52');
INSERT INTO `sys_log` VALUES ('601', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.TTeacherController,[鏂规硶]:addPage,[鍙傛暟]:', null, '2017-12-27 19:08:11');
INSERT INTO `sys_log` VALUES ('602', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.TTeacherCourseController,[鏂规硶]:addPage,[鍙傛暟]:', null, '2017-12-27 19:08:59');
INSERT INTO `sys_log` VALUES ('603', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.TTeacherCourseController,[鏂规硶]:addPage,[鍙傛暟]:', null, '2017-12-27 19:09:43');
INSERT INTO `sys_log` VALUES ('604', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.TTeacherCourseController,[鏂规硶]:addPage,[鍙傛暟]:', null, '2017-12-27 19:13:21');
INSERT INTO `sys_log` VALUES ('605', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.TTeacherCourseController,[鏂规硶]:addPage,[鍙傛暟]:', null, '2017-12-27 19:16:01');
INSERT INTO `sys_log` VALUES ('606', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.TTeacherCourseController,[鏂规硶]:add,[鍙傛暟]:courseId=1&teacherId=1&', '0:0:0:0:0:0:0:1', '2017-12-27 19:16:08');
INSERT INTO `sys_log` VALUES ('607', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.TTeacherCourseController,[鏂规硶]:editPage,[鍙傛暟]:id=1&_=1514373194573&', '0:0:0:0:0:0:0:1', '2017-12-27 19:16:15');
INSERT INTO `sys_log` VALUES ('608', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.TTeacherCourseController,[鏂规硶]:editPage,[鍙傛暟]:id=1&_=1514373194574&', '0:0:0:0:0:0:0:1', '2017-12-27 19:18:22');
INSERT INTO `sys_log` VALUES ('609', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.TTeacherCourseController,[鏂规硶]:editPage,[鍙傛暟]:id=1&_=1514373194575&', '0:0:0:0:0:0:0:1', '2017-12-27 19:18:40');
INSERT INTO `sys_log` VALUES ('610', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.TTeacherCourseController,[鏂规硶]:editPage,[鍙傛暟]:id=1&_=1514373194576&', '0:0:0:0:0:0:0:1', '2017-12-27 19:18:51');
INSERT INTO `sys_log` VALUES ('611', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.TTeacherCourseController,[鏂规硶]:addPage,[鍙傛暟]:', null, '2017-12-27 19:41:14');
INSERT INTO `sys_log` VALUES ('612', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.TTeacherCourseController,[鏂规硶]:addPage,[鍙傛暟]:', null, '2017-12-27 19:41:27');
INSERT INTO `sys_log` VALUES ('613', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.SysListKeyController,[鏂规硶]:addPage,[鍙傛暟]:', null, '2017-12-27 19:41:58');
INSERT INTO `sys_log` VALUES ('614', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.TTeacherCourseController,[鏂规硶]:addPage,[鍙傛暟]:', null, '2017-12-27 19:42:06');
INSERT INTO `sys_log` VALUES ('615', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.TTeacherController,[鏂规硶]:addPage,[鍙傛暟]:', null, '2017-12-27 19:42:22');
INSERT INTO `sys_log` VALUES ('616', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.TTeacherController,[鏂规硶]:add,[鍙傛暟]:name=王五&gender=1&empNo=SN55556666&idNo=44828120000611351x&birthday=2017-12-29&degree=40&deptId=3&title=助教&mobile=13511112222&qq=455644&email=123@163.com&address=墨尔本&', '0:0:0:0:0:0:0:1', '2017-12-27 19:44:09');
INSERT INTO `sys_log` VALUES ('617', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.TTeacherCourseController,[鏂规硶]:addPage,[鍙傛暟]:', null, '2017-12-27 19:44:14');
INSERT INTO `sys_log` VALUES ('618', 'admin', 'admin', '[绫诲悕]:com.mgrsys.controller.TTeacherCourseController,[鏂规硶]:add,[鍙傛暟]:courseId=2&teacherId=2&', '0:0:0:0:0:0:0:1', '2017-12-27 19:44:18');
INSERT INTO `sys_log` VALUES ('619', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:addPage,[参数]:', null, '2017-12-27 21:23:25');
INSERT INTO `sys_log` VALUES ('620', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:add,[参数]:name=工资管理&resourceType=0&url=&openMode=无(用于上层菜单)&icon=fi-folder&seq=0&status=0&opened=1&pid=&', '127.0.0.1', '2017-12-27 21:23:54');
INSERT INTO `sys_log` VALUES ('621', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:addPage,[参数]:', null, '2017-12-27 21:24:02');
INSERT INTO `sys_log` VALUES ('622', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:add,[参数]:name=工资记录&resourceType=0&url=/tSalary/manager&openMode=ajax&icon=fi-list&seq=0&status=0&opened=1&pid=258&', '127.0.0.1', '2017-12-27 21:29:34');
INSERT INTO `sys_log` VALUES ('623', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:addPage,[参数]:', null, '2017-12-27 21:29:40');
INSERT INTO `sys_log` VALUES ('624', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:addPage,[参数]:', null, '2017-12-27 21:56:36');
INSERT INTO `sys_log` VALUES ('625', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:add,[参数]:name=工资结算&resourceType=0&url=/tSalary/add&openMode=ajax&icon=fi-page-add&seq=1&status=0&opened=1&pid=259&', '127.0.0.1', '2017-12-27 21:59:21');
INSERT INTO `sys_log` VALUES ('626', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:editPage,[参数]:id=260&_=1514380893260&', '127.0.0.1', '2017-12-27 21:59:50');
INSERT INTO `sys_log` VALUES ('627', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:edit,[参数]:id=260&name=工资结算&resourceType=1&url=/tSalary/add&openMode=ajax&icon=fi-page-add&seq=1&status=0&opened=1&pid=259&', '127.0.0.1', '2017-12-27 22:00:01');
INSERT INTO `sys_log` VALUES ('628', 'admin', 'admin', '[类名]:com.mgrsys.controller.RoleController,[方法]:grantPage,[参数]:id=1&_=1514380893262&', '127.0.0.1', '2017-12-27 22:00:18');
INSERT INTO `sys_log` VALUES ('629', 'admin', 'admin', '[类名]:com.mgrsys.controller.RoleController,[方法]:grant,[参数]:id=1&resourceIds=1,11,111,112,113,114,12,121,122,123,124,125,13,131,132,133,134,14,141,142,143,144,258,259,260,237,242,238,239,240,241,243,244,245,246,247,248,249,250,251,252,222,223,224,232,233,234,235,253,254,255,256,257,221,227,228,226&', '127.0.0.1', '2017-12-27 22:00:35');
INSERT INTO `sys_log` VALUES ('630', 'admin', 'admin', '[类名]:com.mgrsys.controller.LoginController,[方法]:logout,[参数]:', null, '2017-12-27 22:00:42');
INSERT INTO `sys_log` VALUES ('631', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-27 22:02:25');
INSERT INTO `sys_log` VALUES ('632', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:editPage,[参数]:id=260&_=1514383263962&', '127.0.0.1', '2017-12-27 22:02:38');
INSERT INTO `sys_log` VALUES ('633', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:edit,[参数]:id=260&name=工资结算&resourceType=0&url=/tSalary/addPage&openMode=ajax&icon=fi-page-add&seq=1&status=0&opened=1&pid=258&', '127.0.0.1', '2017-12-27 22:03:21');
INSERT INTO `sys_log` VALUES ('634', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-27 22:03:27');
INSERT INTO `sys_log` VALUES ('635', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-27 22:08:37');
INSERT INTO `sys_log` VALUES ('636', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-27 22:09:44');
INSERT INTO `sys_log` VALUES ('637', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-27 22:10:18');
INSERT INTO `sys_log` VALUES ('638', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-27 22:12:40');
INSERT INTO `sys_log` VALUES ('639', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-27 22:14:51');
INSERT INTO `sys_log` VALUES ('640', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-27 23:13:39');
INSERT INTO `sys_log` VALUES ('641', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-27 23:21:16');
INSERT INTO `sys_log` VALUES ('642', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-27 23:21:25');
INSERT INTO `sys_log` VALUES ('643', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-27 23:22:54');
INSERT INTO `sys_log` VALUES ('644', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-27 23:22:57');
INSERT INTO `sys_log` VALUES ('645', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-27 23:24:29');
INSERT INTO `sys_log` VALUES ('646', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-27 23:24:49');
INSERT INTO `sys_log` VALUES ('647', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-27 23:25:16');
INSERT INTO `sys_log` VALUES ('648', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-27 23:27:45');
INSERT INTO `sys_log` VALUES ('649', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-27 23:28:53');
INSERT INTO `sys_log` VALUES ('650', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-27 23:29:44');
INSERT INTO `sys_log` VALUES ('651', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-27 23:30:05');
INSERT INTO `sys_log` VALUES ('652', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-27 23:30:39');
INSERT INTO `sys_log` VALUES ('653', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-27 23:31:21');
INSERT INTO `sys_log` VALUES ('654', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-27 23:32:37');
INSERT INTO `sys_log` VALUES ('655', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-27 23:33:45');
INSERT INTO `sys_log` VALUES ('656', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-27 23:34:32');
INSERT INTO `sys_log` VALUES ('657', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-27 23:35:10');
INSERT INTO `sys_log` VALUES ('658', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-27 23:35:56');
INSERT INTO `sys_log` VALUES ('659', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-27 23:37:30');
INSERT INTO `sys_log` VALUES ('660', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-27 23:37:53');
INSERT INTO `sys_log` VALUES ('661', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-27 23:38:20');
INSERT INTO `sys_log` VALUES ('662', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-27 23:38:50');
INSERT INTO `sys_log` VALUES ('663', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-27 23:39:38');
INSERT INTO `sys_log` VALUES ('664', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-27 23:41:00');
INSERT INTO `sys_log` VALUES ('665', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:addPage,[参数]:', null, '2017-12-27 23:41:26');
INSERT INTO `sys_log` VALUES ('666', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:add,[参数]:name=结算&resourceType=0&url=/tSalary/calc&openMode=ajax&icon=fi-page-add&seq=0&status=0&opened=1&pid=260&', '127.0.0.1', '2017-12-27 23:43:41');
INSERT INTO `sys_log` VALUES ('667', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:editPage,[参数]:id=261&_=1514383264000&', '127.0.0.1', '2017-12-27 23:44:00');
INSERT INTO `sys_log` VALUES ('668', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:edit,[参数]:id=261&name=结算&resourceType=1&url=/tSalary/calc&openMode=ajax&icon=fi-page-add&seq=0&status=0&opened=1&pid=260&', '127.0.0.1', '2017-12-27 23:44:09');
INSERT INTO `sys_log` VALUES ('669', 'admin', 'admin', '[类名]:com.mgrsys.controller.RoleController,[方法]:grantPage,[参数]:id=1&_=1514383264002&', '127.0.0.1', '2017-12-27 23:44:17');
INSERT INTO `sys_log` VALUES ('670', 'admin', 'admin', '[类名]:com.mgrsys.controller.RoleController,[方法]:grant,[参数]:id=1&resourceIds=1,11,111,112,113,114,12,121,122,123,124,125,13,131,132,133,134,14,141,142,143,144,258,259,260,261,237,242,238,239,240,241,243,244,245,246,247,248,249,250,251,252,222,223,224,232,233,234,235,253,254,255,256,257,221,227,228,226&', '127.0.0.1', '2017-12-27 23:44:28');
INSERT INTO `sys_log` VALUES ('671', 'admin', 'admin', '[类名]:com.mgrsys.controller.LoginController,[方法]:logout,[参数]:', null, '2017-12-27 23:44:38');
INSERT INTO `sys_log` VALUES ('672', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-27 23:45:00');
INSERT INTO `sys_log` VALUES ('673', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-27 23:47:06');
INSERT INTO `sys_log` VALUES ('674', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-27 23:47:58');
INSERT INTO `sys_log` VALUES ('675', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-27 23:55:09');
INSERT INTO `sys_log` VALUES ('676', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-27 23:56:48');
INSERT INTO `sys_log` VALUES ('677', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-27 23:57:35');
INSERT INTO `sys_log` VALUES ('678', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-27 23:58:25');
INSERT INTO `sys_log` VALUES ('679', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-28 00:19:57');
INSERT INTO `sys_log` VALUES ('680', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-28 00:20:28');
INSERT INTO `sys_log` VALUES ('681', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:add,[参数]:teacherId=1&', '127.0.0.1', '2017-12-28 00:20:32');
INSERT INTO `sys_log` VALUES ('682', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-28 00:21:40');
INSERT INTO `sys_log` VALUES ('683', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:add,[参数]:teacherId=1&', '127.0.0.1', '2017-12-28 00:21:53');
INSERT INTO `sys_log` VALUES ('684', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-28 00:24:16');
INSERT INTO `sys_log` VALUES ('685', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-28 00:24:56');
INSERT INTO `sys_log` VALUES ('686', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:add,[参数]:teacherId=1&', '127.0.0.1', '2017-12-28 00:25:00');
INSERT INTO `sys_log` VALUES ('687', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-28 00:26:54');
INSERT INTO `sys_log` VALUES ('688', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:add,[参数]:teacherId=1&', '127.0.0.1', '2017-12-28 00:27:03');
INSERT INTO `sys_log` VALUES ('689', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-28 00:28:20');
INSERT INTO `sys_log` VALUES ('690', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:add,[参数]:teacherId=1&', '127.0.0.1', '2017-12-28 00:28:24');
INSERT INTO `sys_log` VALUES ('691', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2017-12-28 00:30:23');
INSERT INTO `sys_log` VALUES ('692', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:add,[参数]:teacherId=2&', '127.0.0.1', '2017-12-28 00:30:29');
INSERT INTO `sys_log` VALUES ('693', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:addPage,[参数]:', null, '2017-12-28 00:49:47');
INSERT INTO `sys_log` VALUES ('694', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:add,[参数]:name=报表管理&resourceType=0&url=&openMode=无(用于上层菜单)&icon=fi-folder&seq=0&status=0&opened=1&pid=&', '127.0.0.1', '2017-12-28 00:50:16');
INSERT INTO `sys_log` VALUES ('695', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:addPage,[参数]:', null, '2017-12-28 00:50:52');
INSERT INTO `sys_log` VALUES ('696', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:add,[参数]:name=教师职称统计&resourceType=0&url=&openMode=ajax&icon=&seq=0&status=0&opened=1&pid=262&', '127.0.0.1', '2017-12-28 00:51:48');
INSERT INTO `sys_log` VALUES ('697', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:editPage,[参数]:id=263&_=1514393049433&', '127.0.0.1', '2017-12-28 00:51:57');
INSERT INTO `sys_log` VALUES ('698', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:edit,[参数]:id=263&name=教师职称统计&resourceType=0&url=&openMode=ajax&icon=fi-home&seq=0&status=0&opened=1&pid=262&', '127.0.0.1', '2017-12-28 00:52:12');
INSERT INTO `sys_log` VALUES ('699', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:editPage,[参数]:id=263&_=1514393049434&', '127.0.0.1', '2017-12-28 01:06:21');
INSERT INTO `sys_log` VALUES ('700', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:edit,[参数]:id=263&name=教师职称统计&resourceType=0&url=/report/teacherReportView&openMode=ajax&icon=fi-home&seq=0&status=0&opened=1&pid=262&', '127.0.0.1', '2017-12-28 01:06:40');
INSERT INTO `sys_log` VALUES ('701', 'admin', 'admin', '[类名]:com.mgrsys.controller.TTeacherController,[方法]:addPage,[参数]:', null, '2017-12-28 02:06:00');
INSERT INTO `sys_log` VALUES ('702', 'admin', 'admin', '[类名]:com.mgrsys.controller.TTeacherController,[方法]:add,[参数]:name=张莎&gender=0&empNo=SN098773&idNo=444272003124598&birthday=2017-12-29&degree=10&deptId=1&title=教授&mobile=13222224444&qq=9088888&email=123@qq.com&address=火星&', '127.0.0.1', '2017-12-28 02:07:35');
INSERT INTO `sys_log` VALUES ('703', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:editPage,[参数]:id=235&_=1514393049466&', '127.0.0.1', '2017-12-28 02:11:52');
INSERT INTO `sys_log` VALUES ('704', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:edit,[参数]:id=235&name=信息统计&resourceType=0&url=/tTeacher/statisticDataGrid&openMode=ajax&icon=fi-home&seq=0&status=0&opened=1&pid=222&', '127.0.0.1', '2017-12-28 02:12:16');
INSERT INTO `sys_log` VALUES ('705', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:editPage,[参数]:id=235&_=1514393049469&', '127.0.0.1', '2017-12-28 02:14:45');
INSERT INTO `sys_log` VALUES ('706', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:edit,[参数]:id=235&name=信息统计&resourceType=0&url=/tTeacher/statisticView&openMode=ajax&icon=fi-home&seq=0&status=0&opened=1&pid=222&', '127.0.0.1', '2017-12-28 02:15:00');
INSERT INTO `sys_log` VALUES ('707', 'admin', 'admin', '[类名]:com.mgrsys.controller.RoleController,[方法]:grantPage,[参数]:id=1&_=1514393049471&', '127.0.0.1', '2017-12-28 02:15:07');
INSERT INTO `sys_log` VALUES ('708', 'admin', 'admin', '[类名]:com.mgrsys.controller.RoleController,[方法]:grant,[参数]:id=1&resourceIds=1,11,111,112,113,114,12,121,122,123,124,125,13,131,132,133,134,14,141,142,143,144,258,259,260,261,237,242,238,239,240,241,243,244,245,246,247,248,249,250,251,252,222,223,224,232,233,234,235,253,254,255,256,257,221,227,228,226&', '127.0.0.1', '2017-12-28 02:15:14');
INSERT INTO `sys_log` VALUES ('709', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:editPage,[参数]:id=133&_=1578049371908&', '0:0:0:0:0:0:0:1', '2020-01-03 19:03:02');
INSERT INTO `sys_log` VALUES ('710', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:editPage,[参数]:id=111&_=1578049371909&', '0:0:0:0:0:0:0:1', '2020-01-03 19:06:39');
INSERT INTO `sys_log` VALUES ('711', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:editPage,[参数]:id=123&_=1578049371910&', '0:0:0:0:0:0:0:1', '2020-01-03 19:06:42');
INSERT INTO `sys_log` VALUES ('712', 'admin', 'admin', '[类名]:com.mgrsys.controller.ResourceController,[方法]:edit,[参数]:id=123&name=编辑&resourceType=1&url=/role/edit&openMode=ajax&icon=fi-page-edit&seq=0&status=0&opened=1&pid=12&', '0:0:0:0:0:0:0:1', '2020-01-03 19:06:44');
INSERT INTO `sys_log` VALUES ('713', 'admin', 'admin', '[类名]:com.mgrsys.controller.TSalaryController,[方法]:addPage,[参数]:', null, '2020-01-03 19:06:57');

-- ----------------------------
-- Table structure for t_course
-- ----------------------------
DROP TABLE IF EXISTS `t_course`;
CREATE TABLE `t_course` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '课程编号',
  `t_name` varchar(50) DEFAULT NULL COMMENT '课程名称',
  `credit` int(11) DEFAULT NULL COMMENT '学分',
  `t_hour` int(11) DEFAULT NULL COMMENT '课时',
  `dk_level` int(11) DEFAULT NULL COMMENT '代课金级别',
  `create_time` date DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  `delete_flag` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='课程表';

-- ----------------------------
-- Records of t_course
-- ----------------------------
INSERT INTO `t_course` VALUES ('1', '高等数学', '10', '5', '10', '2017-12-27', '2017-12-27', '0');
INSERT INTO `t_course` VALUES ('2', '线性代数', '2', '20', '30', '2017-12-27', '2017-12-27', '0');

-- ----------------------------
-- Table structure for t_dept
-- ----------------------------
DROP TABLE IF EXISTS `t_dept`;
CREATE TABLE `t_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系部编号',
  `dept_name` varchar(50) DEFAULT NULL COMMENT '系部名称',
  `standby1` varchar(50) DEFAULT NULL COMMENT '备用字段1',
  `standby2` varchar(50) DEFAULT NULL COMMENT '备用字段2',
  `standby3` varchar(50) DEFAULT NULL COMMENT '备用字段3',
  `create_time` date DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  `delete_flag` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='系部表';

-- ----------------------------
-- Records of t_dept
-- ----------------------------
INSERT INTO `t_dept` VALUES ('1', '理学院', null, null, null, '2017-12-27', '2017-12-27', '0');
INSERT INTO `t_dept` VALUES ('2', '商学院', null, null, null, '2017-12-27', '2017-12-27', '0');
INSERT INTO `t_dept` VALUES ('3', '计算机学院', null, null, null, '2017-12-27', '2017-12-27', '1');

-- ----------------------------
-- Table structure for t_salary
-- ----------------------------
DROP TABLE IF EXISTS `t_salary`;
CREATE TABLE `t_salary` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '工资表编号',
  `calc_month` varchar(10) DEFAULT NULL COMMENT '结算期号,例如:20171222',
  `money` int(11) DEFAULT NULL COMMENT '工资',
  `memo` varchar(300) DEFAULT NULL COMMENT '备注',
  `create_time` date DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  `delete_flag` int(11) DEFAULT NULL,
  `teacher_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='工资表';

-- ----------------------------
-- Records of t_salary
-- ----------------------------
INSERT INTO `t_salary` VALUES ('1', '201711', '8500', '20171231工资', '2017-12-27', '2017-12-27', '0', '1');
INSERT INTO `t_salary` VALUES ('2', '201712', '100', '1*100=100', '2017-12-28', '2017-12-28', '0', '1');
INSERT INTO `t_salary` VALUES ('3', '201712', '300', '1*300=300', '2017-12-28', '2017-12-28', '0', '2');

-- ----------------------------
-- Table structure for t_teacher
-- ----------------------------
DROP TABLE IF EXISTS `t_teacher`;
CREATE TABLE `t_teacher` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '教师编号',
  `t_name` varchar(30) DEFAULT NULL COMMENT '教师名称',
  `emp_no` varchar(50) DEFAULT NULL COMMENT '教师职工号',
  `gender` varchar(1) DEFAULT NULL COMMENT '性别:1男0女',
  `id_no` varchar(50) DEFAULT NULL COMMENT '身份证号',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `degree` int(11) DEFAULT NULL COMMENT '学历',
  `dept_id` int(11) DEFAULT NULL COMMENT '部门外键ID(逻辑关联即可)',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机号',
  `qq` varchar(30) DEFAULT NULL COMMENT 'QQ号',
  `email` varchar(30) DEFAULT NULL COMMENT 'Email',
  `address` varchar(200) DEFAULT NULL COMMENT '住址',
  `title` varchar(50) DEFAULT NULL COMMENT '职称',
  `create_time` date DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  `delete_flag` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='教师表';

-- ----------------------------
-- Records of t_teacher
-- ----------------------------
INSERT INTO `t_teacher` VALUES ('1', '张三', 'SN13111222', '1', '445231245654x', '2017-12-21', '10', '1', '13211115555', '12314556', '123@qq.com', '北京清华园', '教授', '2017-12-27', '2017-12-27', '0');
INSERT INTO `t_teacher` VALUES ('2', '王五', 'SN55556666', '1', '44828120000611351x', '2017-12-29', '40', '3', '13511112222', '455644', '123@163.com', '墨尔本', '助教', '2017-12-27', '2017-12-27', '0');
INSERT INTO `t_teacher` VALUES ('3', '张莎', 'SN098773', '0', '444272003124598', '2017-12-29', '10', '1', '13222224444', '9088888', '123@qq.com', '火星', '教授', '2017-12-28', '2017-12-28', '0');

-- ----------------------------
-- Table structure for t_teacher_course
-- ----------------------------
DROP TABLE IF EXISTS `t_teacher_course`;
CREATE TABLE `t_teacher_course` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `teacher_id` int(11) DEFAULT NULL COMMENT '教师id',
  `course_id` int(11) DEFAULT NULL COMMENT '课程表id',
  `create_time` date DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  `delete_flag` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='教师课程中间表';

-- ----------------------------
-- Records of t_teacher_course
-- ----------------------------
INSERT INTO `t_teacher_course` VALUES ('1', '1', '1', '2017-12-27', '2017-12-27', '0');
INSERT INTO `t_teacher_course` VALUES ('2', '2', '2', '2017-12-27', '2017-12-27', '0');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `login_name` varchar(64) NOT NULL COMMENT '登陆名',
  `name` varchar(64) NOT NULL COMMENT '用户名',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `salt` varchar(36) DEFAULT NULL COMMENT '密码加密盐',
  `sex` tinyint(2) NOT NULL DEFAULT '0' COMMENT '性别',
  `age` tinyint(2) DEFAULT '0' COMMENT '年龄',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `user_type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '用户类别',
  `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '用户状态',
  `organization_id` int(11) NOT NULL DEFAULT '0' COMMENT '所属机构',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDx_user_login_name` (`login_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='用户';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', 'admin', '9283a03246ef2dacdc21a9b137817ec1', 'test', '0', '25', '18707173376', '0', '0', '1', '2015-12-06 13:14:05');
INSERT INTO `user` VALUES ('13', 'snoopy', 'snoopy', '05a671c66aefea124cc08b76ea6d30bb', 'test', '0', '25', '18707173376', '1', '0', '3', '2015-10-01 13:12:07');
INSERT INTO `user` VALUES ('14', 'dreamlu', 'dreamlu', '05a671c66aefea124cc08b76ea6d30bb', 'test', '0', '25', '18707173376', '1', '0', '5', '2015-10-11 23:12:58');
INSERT INTO `user` VALUES ('15', 'test', 'test', '05a671c66aefea124cc08b76ea6d30bb', 'test', '0', '25', '18707173376', '1', '0', '6', '2015-12-06 13:13:03');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint(19) NOT NULL COMMENT '用户id',
  `role_id` bigint(19) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`),
  KEY `idx_user_role_ids` (`user_id`,`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8 COMMENT='用户角色';

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('60', '1', '1');
INSERT INTO `user_role` VALUES ('61', '1', '2');
INSERT INTO `user_role` VALUES ('62', '1', '7');
INSERT INTO `user_role` VALUES ('65', '1', '8');
INSERT INTO `user_role` VALUES ('63', '13', '2');
INSERT INTO `user_role` VALUES ('64', '14', '7');
INSERT INTO `user_role` VALUES ('53', '15', '8');
