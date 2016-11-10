/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50149
Source Host           : localhost:3306
Source Database       : pismo_products_teste

Target Server Type    : MYSQL
Target Server Version : 50149
File Encoding         : 65001

Date: 2016-08-03 17:00:06
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `rw_customer`
-- ----------------------------
DROP TABLE IF EXISTS `rw_customer`;
CREATE TABLE `rw_customer` (
  `cod_customer` bigint(20) NOT NULL AUTO_INCREMENT,
  `dsc_name` varchar(255) DEFAULT NULL,
  `dsc_pass` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cod_customer`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rw_customer
-- ----------------------------
INSERT INTO rw_customer VALUES ('1', 'teste', 'jUj9bKW8652XRQLotzx1kA==');

-- ----------------------------
-- Table structure for `rw_order`
-- ----------------------------
DROP TABLE IF EXISTS `rw_order`;
CREATE TABLE `rw_order` (
  `cod_order` varchar(255) NOT NULL,
  `cod_customer` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`cod_order`),
  KEY `FK81B970D43DF3E81A` (`cod_customer`),
  CONSTRAINT `FK81B970D43DF3E81A` FOREIGN KEY (`cod_customer`) REFERENCES `rw_customer` (`cod_customer`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rw_order
-- ----------------------------

-- ----------------------------
-- Table structure for `rw_product`
-- ----------------------------
DROP TABLE IF EXISTS `rw_product`;
CREATE TABLE `rw_product` (
  `cod_product` int(11) NOT NULL AUTO_INCREMENT,
  `dsc_description` varchar(255) DEFAULT NULL,
  `dsc_name` varchar(255) DEFAULT NULL,
  `vlr_currency_value` varchar(255) DEFAULT NULL,
  `vlr_price_value` decimal(19,2) DEFAULT NULL,
  PRIMARY KEY (`cod_product`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rw_product
-- ----------------------------
INSERT INTO rw_product VALUES ('1', 'teste', 'teste', 'BRL', '100.00');

-- ----------------------------
-- Table structure for `rw_product_lock`
-- ----------------------------
DROP TABLE IF EXISTS `rw_product_lock`;
CREATE TABLE `rw_product_lock` (
  `cod_lock` varchar(255) NOT NULL,
  `cod_customer` bigint(20) NOT NULL,
  `cod_product` int(11) DEFAULT NULL,
  PRIMARY KEY (`cod_lock`,`cod_customer`),
  KEY `FKC0E2E235E7665EE7` (`cod_customer`),
  KEY `FKC0E2E235D1575814` (`cod_product`),
  CONSTRAINT `FKC0E2E235D1575814` FOREIGN KEY (`cod_product`) REFERENCES `rw_product` (`cod_product`),
  CONSTRAINT `FKC0E2E235E7665EE7` FOREIGN KEY (`cod_customer`) REFERENCES `rw_customer` (`cod_customer`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rw_product_lock
-- ----------------------------

-- ----------------------------
-- Table structure for `rw_product_order`
-- ----------------------------
DROP TABLE IF EXISTS `rw_product_order`;
CREATE TABLE `rw_product_order` (
  `cod_product_order` varchar(255) NOT NULL,
  `vlr_currency_product` varchar(255) NOT NULL,
  `dsc_description_product` varchar(255) NOT NULL,
  `dsc_name_product` varchar(255) NOT NULL,
  `vlr_cod_product` int(11) NOT NULL,
  `set_status` int(11) NOT NULL,
  `vlr_price_product` decimal(19,2) NOT NULL,
  `cod_order` varchar(255) NOT NULL,
  PRIMARY KEY (`cod_product_order`),
  KEY `FK5BA50B8462117361` (`cod_order`),
  CONSTRAINT `FK5BA50B8462117361` FOREIGN KEY (`cod_order`) REFERENCES `rw_order` (`cod_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rw_product_order
-- ----------------------------

-- ----------------------------
-- Table structure for `rw_product_stock`
-- ----------------------------
DROP TABLE IF EXISTS `rw_product_stock`;
CREATE TABLE `rw_product_stock` (
  `cod_stock` int(11) NOT NULL,
  `vlr_stock` int(11) NOT NULL,
  `cod_product` int(11) NOT NULL,
  PRIMARY KEY (`cod_stock`),
  KEY `FK5BDE7B4CD1575814` (`cod_product`),
  CONSTRAINT `FK5BDE7B4CD1575814` FOREIGN KEY (`cod_product`) REFERENCES `rw_product` (`cod_product`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rw_product_stock
-- ----------------------------
INSERT INTO rw_product_stock VALUES ('1', '86', '1');
