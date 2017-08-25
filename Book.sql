/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50616
Source Host           : localhost:3306
Source Database       : zhaoyaoba

Target Server Type    : MYSQL
Target Server Version : 50616
File Encoding         : 65001

Date: 2016-09-14 00:00:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `book`
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `bookId` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime DEFAULT NULL,
  `numberCode` varchar(255) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `drugStroeId` bigint(20) DEFAULT NULL,
  `deliveryId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`bookId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of book
-- ----------------------------

-- ----------------------------
-- Table structure for `delivery`
-- ----------------------------
DROP TABLE IF EXISTS `delivery`;
CREATE TABLE `delivery` (
  `deliveryId` bigint(20) NOT NULL AUTO_INCREMENT,
  `deliveryName` varchar(255) DEFAULT NULL,
  `deliveryPhone` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  PRIMARY KEY (`deliveryId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of delivery
-- ----------------------------
INSERT INTO `delivery` VALUES ('1', 'liuwei', '110', '123', '2016-09-13 22:02:44', null);

-- ----------------------------
-- Table structure for `drugstoreinfo`
-- ----------------------------
DROP TABLE IF EXISTS `drugstoreinfo`;
CREATE TABLE `drugstoreinfo` (
  `drugStroeId` bigint(20) NOT NULL AUTO_INCREMENT,
  `drugStroeName` varchar(255) DEFAULT NULL,
  `drugStorePhone` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  PRIMARY KEY (`drugStroeId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of drugstoreinfo
-- ----------------------------
INSERT INTO `drugstoreinfo` VALUES ('1', 'liuwei', '13812115207', '123', '2016-09-13 22:02:20', null);

-- ----------------------------
-- Table structure for `number`
-- ----------------------------
DROP TABLE IF EXISTS `number`;
CREATE TABLE `number` (
  `numberId` bigint(20) NOT NULL AUTO_INCREMENT,
  `numberCode` varchar(255) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `drugStroeId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`numberId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of number
-- ----------------------------
INSERT INTO `number` VALUES ('1', '9570', '2016-09-13 23:43:25', '1');
INSERT INTO `number` VALUES ('2', '392', '2016-09-13 23:45:57', '1');
INSERT INTO `number` VALUES ('3', '9018', '2016-09-13 23:52:51', '1');
INSERT INTO `number` VALUES ('4', '8943', '2016-09-13 23:53:27', '1');
INSERT INTO `number` VALUES ('5', '2233', '2016-09-13 23:54:37', '1');
INSERT INTO `number` VALUES ('6', '7654', '2016-09-13 23:55:38', '1');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userId` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'liuwei', '17713601564', '2016-09-13 22:01:49', '123');
