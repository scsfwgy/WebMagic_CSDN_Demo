/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50517
Source Host           : localhost:3306
Source Database       : spider_csdn_demo

Target Server Type    : MYSQL
Target Server Version : 50517
File Encoding         : 65001

Date: 2016-10-05 12:36:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `author`
-- ----------------------------
DROP TABLE IF EXISTS `author`;
CREATE TABLE `author` (
  `id` varchar(100) NOT NULL,
  `id_author` varchar(100) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `headImg` varchar(255) DEFAULT NULL,
  `viewNums` varchar(10) DEFAULT NULL,
  `points` varchar(10) DEFAULT NULL,
  `rank` varchar(10) DEFAULT NULL,
  `originalNums` varchar(10) DEFAULT NULL,
  `repuishNums` varchar(10) DEFAULT NULL,
  `translateNums` varchar(10) DEFAULT NULL,
  `commentNums` varchar(10) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `descb` varchar(500) DEFAULT NULL,
  `isBlogExpert` tinyint(1) NOT NULL,
  `isPreBlogExpert` tinyint(1) NOT NULL,
  `isPersist` tinyint(1) NOT NULL,
  `isColumnUp` tinyint(1) NOT NULL,
  `isBlogStars` tinyint(1) NOT NULL,
  `isMicrMvp` tinyint(1) NOT NULL,
  `blogColumns` text,
  `createTime` varchar(100) DEFAULT NULL,
  `stamp` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `spare1` varchar(255) DEFAULT NULL,
  `spare2` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_author` (`id_author`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of author
-- ----------------------------

-- ----------------------------
-- Table structure for `blog`
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog` (
  `id` varchar(100) NOT NULL,
  `id_blog` varchar(255) DEFAULT NULL,
  `author` varchar(50) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `isTop` int(10) DEFAULT NULL,
  `type` int(10) DEFAULT NULL,
  `detailsUrl` varchar(100) DEFAULT NULL,
  `publishDateTime` varchar(50) DEFAULT NULL,
  `viewNums` int(11) DEFAULT NULL,
  `commentNums` int(11) DEFAULT NULL,
  `summary` text,
  `details` text,
  `upNums` int(11) DEFAULT NULL,
  `downNums` int(11) DEFAULT NULL,
  `id_author` varchar(100) DEFAULT NULL,
  `createTime` varchar(50) DEFAULT NULL,
  `stamp` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `spare1` varchar(255) DEFAULT NULL,
  `spare2` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_blog` (`id_blog`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of blog
-- ----------------------------

-- ----------------------------
-- Table structure for `comment`
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` varchar(100) NOT NULL,
  `id_comment` varchar(100) DEFAULT NULL,
  `fromId` varchar(100) DEFAULT NULL,
  `fromUser` varchar(100) DEFAULT NULL,
  `fromDataTime` varchar(100) DEFAULT NULL,
  `fromHeadImg` varchar(225) DEFAULT NULL,
  `fromContent` text,
  `toId` varchar(100) DEFAULT NULL,
  `id_author` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_comment` (`id_comment`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment
-- ----------------------------

-- ----------------------------
-- Table structure for `mine`
-- ----------------------------
DROP TABLE IF EXISTS `mine`;
CREATE TABLE `mine` (
  `id` varchar(100) NOT NULL,
  `id_mine` varchar(100) DEFAULT NULL,
  `nickName` varchar(100) DEFAULT NULL,
  `headImg` varchar(225) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `birthday` varchar(50) DEFAULT NULL,
  `focusNums` varchar(10) DEFAULT NULL,
  `fansNums` varchar(10) DEFAULT NULL,
  `sign` text,
  `personDetail` text,
  `trade` varchar(255) DEFAULT NULL,
  `job` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `nation` varchar(255) DEFAULT NULL,
  `province` varchar(50) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `area` varchar(50) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `stamp` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `spare1` varchar(255) DEFAULT NULL,
  `spare2` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_mine` (`id_mine`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mine
-- ----------------------------

-- ----------------------------
-- View structure for `v_mine_descbyfansnums`
-- ----------------------------
DROP VIEW IF EXISTS `v_mine_descbyfansnums`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_mine_descbyfansnums` AS select `mine`.`id` AS `id`,`mine`.`id_mine` AS `id_mine`,`mine`.`nickName` AS `nickName`,`mine`.`headImg` AS `headImg`,`mine`.`name` AS `name`,`mine`.`sex` AS `sex`,`mine`.`birthday` AS `birthday`,`mine`.`focusNums` AS `focusNums`,`mine`.`fansNums` AS `fansNums`,`mine`.`sign` AS `sign`,`mine`.`personDetail` AS `personDetail`,`mine`.`trade` AS `trade`,`mine`.`job` AS `job`,`mine`.`location` AS `location`,`mine`.`nation` AS `nation`,`mine`.`province` AS `province`,`mine`.`city` AS `city`,`mine`.`area` AS `area`,`mine`.`createTime` AS `createTime`,`mine`.`stamp` AS `stamp`,`mine`.`spare1` AS `spare1`,`mine`.`spare2` AS `spare2` from `mine` order by cast(`mine`.`fansNums` as signed) desc ;
