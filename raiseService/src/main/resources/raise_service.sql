/*
 Navicat Premium Data Transfer

 Source Server         : mysql[服务器]
 Source Server Type    : MySQL
 Source Server Version : 80016
 Source Host           : 47.111.127.219:3306
 Source Schema         : raise_service

 Target Server Type    : MySQL
 Target Server Version : 80016
 File Encoding         : 65001

 Date: 03/07/2021 14:33:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `location` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `demand` int(11) NOT NULL,
  `raised` int(11) NOT NULL DEFAULT 0,
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (29, '口罩', '四川-成都', 200, 100, 'R.mipmap.kz');
INSERT INTO `goods` VALUES (30, '护目镜', '重庆', 50, 30, 'R.mipmap.hmj');
INSERT INTO `goods` VALUES (31, '消毒液', '北京', 3000, 200, 'R.mipmap.xdy');
INSERT INTO `goods` VALUES (32, '防护服', '海南', 1000, 0, 'R.mipmap.fhf');
INSERT INTO `goods` VALUES (33, '口罩', '湖南-长沙', 5000, 500, 'R.mipmap.kz');
INSERT INTO `goods` VALUES (34, '防护服', '上海', 500, 0, 'R.mipmap.fhf');

-- ----------------------------
-- Table structure for userinfo
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `level` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 53 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO `userinfo` VALUES (28, 'vvi', 'vvi', '管理员');
INSERT INTO `userinfo` VALUES (29, 'mm', '1234', '普通用户');
INSERT INTO `userinfo` VALUES (41, 'mmm', '123456', '普通用户');
INSERT INTO `userinfo` VALUES (42, '管理员', '123', '管理员');
INSERT INTO `userinfo` VALUES (43, 'tom', '123', '普通用户');
INSERT INTO `userinfo` VALUES (45, '啦啦', '1234', '普通用户');
INSERT INTO `userinfo` VALUES (49, 'Tomcat', '1234', '普通用户');
INSERT INTO `userinfo` VALUES (50, 'qwer', '1234', '管理员');
INSERT INTO `userinfo` VALUES (53, 'hello', '1234', '普通用户');

SET FOREIGN_KEY_CHECKS = 1;
