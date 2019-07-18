/*
Navicat MySQL Data Transfer

Source Server         : 123
Source Server Version : 50724
Source Host           : 123.207.231.159:3306
Source Database       : security

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2019-07-18 14:56:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `permission`
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` int(9) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `role` varchar(20) NOT NULL,
  `url` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('1', '用户', 'user', '/user');
INSERT INTO `permission` VALUES ('2', '管理员', 'admin', '/home');
INSERT INTO `permission` VALUES ('3', '管理员', 'admin', '/user');

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(9) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('3', 'zzx', 'user');
INSERT INTO `role` VALUES ('4', 'qq', 'admin');
INSERT INTO `role` VALUES ('5', 'lz', 'admin');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_uuid` varchar(70) DEFAULT '1',
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT '11016482042qq.com',
  `telephone` varchar(255) DEFAULT '18628395765',
  `last_ip` varchar(255) DEFAULT '127.0.0.1',
  `last_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('13', '1', 'zzx', '$2a$04$KyR3tAMSIjyeMDoWZ3L0BuyMAq0R.Fz.MUeBHsUYQYlh5v/LqvLQW', '11016482042qq.com', '12234532242', '127.0.0.1', '2019-07-18 11:43:02');
INSERT INTO `user` VALUES ('14', '1', 'qq', '$2a$04$.I4BSHz4ZX4OmPWbRgH9G.LXLLUZuVi39BB6NNIcdqi92qvHOD5Yq', '11016482042qq.com', '12234532242', '127.0.0.1', '2019-07-18 12:21:34');
INSERT INTO `user` VALUES ('15', '1', 'lz', '$2a$04$OHNOwfFVZQAUSN8znrhRCeRLUOc8VDf4H0OWdH8gWYB4UJmDj54Hq', '11016482042qq.com', '12234532242', '127.0.0.1', '2019-07-18 14:53:35');
