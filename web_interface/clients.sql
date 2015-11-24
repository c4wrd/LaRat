-- Use this to create the tables needed for LaRat

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

DROP TABLE IF EXISTS `larat_clients`;
CREATE TABLE `larat_clients` (
  `objectId` varchar(50) CHARACTER SET utf8 NOT NULL,
  `carrier` varchar(15) CHARACTER SET utf8 NOT NULL,
  `phoneNumber` varchar(10) CHARACTER SET utf8 NOT NULL,
  `deviceid` varchar(25) CHARACTER SET utf8 NOT NULL,
  `sdkversion` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `clients`;
CREATE TABLE `clients` (
  `objectId` varchar(50) CHARACTER SET utf8 NOT NULL,
  `carrier` varchar(15) CHARACTER SET utf8 NOT NULL,
  `phoneNumber` varchar(10) CHARACTER SET utf8 NOT NULL,
  `deviceid` varchar(25) CHARACTER SET utf8 NOT NULL,
  `sdkversion` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `client_messages`;
CREATE TABLE `client_messages` (
  `objectId` varchar(50) CHARACTER SET utf8 NOT NULL,
  `message_type` varchar(50) NOT NULL,
  `message` mediumtext CHARACTER SET utf8 NOT NULL,
  `unread` tinyint(1) NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `location_history`;
CREATE TABLE `location_history` (
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `latitude` float NOT NULL,
  `longitude` float NOT NULL,
  `objectId` varchar(10) CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- 2015-10-05 20:55:00
