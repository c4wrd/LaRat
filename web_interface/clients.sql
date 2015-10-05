-- phpMyAdmin SQL Dump
-- version 4.4.14
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Oct 05, 2015 at 12:51 AM
-- Server version: 5.6.26
-- PHP Version: 5.6.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `clients`
--
CREATE DATABASE IF NOT EXISTS `c4wd_clients` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `c4wd_clients`;

-- --------------------------------------------------------

--
-- Table structure for table `clients`
--

CREATE TABLE IF NOT EXISTS `clients` (
  `objectId` varchar(10) CHARACTER SET utf8 NOT NULL,
  `carrier` varchar(15) CHARACTER SET utf8 NOT NULL,
  `phoneNumber` varchar(10) CHARACTER SET utf8 NOT NULL,
  `deviceid` varchar(25) CHARACTER SET utf8 NOT NULL,
  `sdkversion` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `client_messages`
--

CREATE TABLE IF NOT EXISTS `client_messages` (
  `objectId` varchar(10) CHARACTER SET utf8 NOT NULL,
  `message_type` varchar(10) NOT NULL,
  `message` mediumtext CHARACTER SET utf8 NOT NULL,
  `unread` tinyint(1) NOT NULL,
  `time` timestamp DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `client_sms_thread`
--

CREATE TABLE IF NOT EXISTS `client_sms_thread` (
  `id` varchar(8) NOT NULL,
  `message` mediumtext CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `location_history`
--

CREATE TABLE IF NOT EXISTS `location_history` (
  `time` timestamp DEFAULT CURRENT_TIMESTAMP,
  `latitude` float NOT NULL,
  `longitude` float NOT NULL,
  `objectId` varchar(10) CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
