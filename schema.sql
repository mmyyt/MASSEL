-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: masseldb
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `category_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `chat`
--

DROP TABLE IF EXISTS `chat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat` (
  `message_id` int NOT NULL AUTO_INCREMENT,
  `room_id` varchar(255) DEFAULT NULL,
  `sender` varchar(255) DEFAULT NULL,
  `content` text,
  `send_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `recipient` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`message_id`)
) ENGINE=InnoDB AUTO_INCREMENT=134 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `chat_read`
--

DROP TABLE IF EXISTS `chat_read`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat_read` (
  `message_id` int DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `is_read` tinyint(1) DEFAULT '0',
  `room_id` varchar(255) DEFAULT NULL,
  KEY `message_id` (`message_id`),
  CONSTRAINT `chat_read_ibfk_1` FOREIGN KEY (`message_id`) REFERENCES `chat` (`message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `chat_room`
--

DROP TABLE IF EXISTS `chat_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat_room` (
  `room_id` varchar(255) NOT NULL,
  `sender` varchar(255) DEFAULT NULL,
  `recipient` varchar(255) DEFAULT NULL,
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`room_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `chat_room_exit`
--

DROP TABLE IF EXISTS `chat_room_exit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat_room_exit` (
  `room_id` varchar(255) NOT NULL,
  `id` varchar(100) DEFAULT NULL,
  `is_exit` tinyint(1) DEFAULT '0',
  KEY `fk_chat_room_exit_room_id` (`room_id`),
  CONSTRAINT `fk_chat_room_exit_room_id` FOREIGN KEY (`room_id`) REFERENCES `chat_room` (`room_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `default_img`
--

DROP TABLE IF EXISTS `default_img`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `default_img` (
  `uuid` varchar(256) NOT NULL,
  `save_dir` varchar(256) NOT NULL,
  `file_name` varchar(256) NOT NULL,
  `file_type` tinyint(1) DEFAULT '1',
  `file_size` int DEFAULT NULL,
  `reg_date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `demand_survey`
--

DROP TABLE IF EXISTS `demand_survey`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `demand_survey` (
  `dno` int NOT NULL AUTO_INCREMENT,
  `dtitle` varchar(500) NOT NULL,
  `ddetail` text,
  `id` varchar(100) NOT NULL,
  `dview_count` int DEFAULT '0',
  `dis_del` tinyint(1) DEFAULT '0',
  `dis_end` varchar(10) DEFAULT 'false',
  `dreg_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `dparticipate_count` int DEFAULT '0',
  `category_id` int DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `full_date` varchar(100) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`dno`),
  KEY `id` (`id`),
  KEY `fk_category_id` (`category_id`),
  CONSTRAINT `demand_survey_ibfk_1` FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_category_id` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `demand_survey_img`
--

DROP TABLE IF EXISTS `demand_survey_img`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `demand_survey_img` (
  `dno` int DEFAULT NULL,
  `uuid` varchar(256) NOT NULL,
  `save_dir` varchar(256) NOT NULL,
  `file_name` varchar(256) NOT NULL,
  `file_type` tinyint(1) DEFAULT '0',
  `file_size` int DEFAULT NULL,
  `reg_date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `demand_survey_participation`
--

DROP TABLE IF EXISTS `demand_survey_participation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `demand_survey_participation` (
  `dpno` int NOT NULL,
  `id` varchar(100) NOT NULL,
  `dpname` varchar(256) NOT NULL,
  `dpprice` int NOT NULL,
  `count` int DEFAULT NULL,
  `participationDate` datetime DEFAULT CURRENT_TIMESTAMP,
  `dno` int NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `nickname` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `demand_survey_product`
--

DROP TABLE IF EXISTS `demand_survey_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `demand_survey_product` (
  `dno` int DEFAULT NULL,
  `dpno` int NOT NULL AUTO_INCREMENT,
  `dpname` varchar(256) NOT NULL,
  `dpprice` int NOT NULL,
  PRIMARY KEY (`dpno`)
) ENGINE=InnoDB AUTO_INCREMENT=175 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification` (
  `listener` varchar(100) DEFAULT NULL,
  `is_read` tinyint(1) DEFAULT '0',
  `recieved_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `message_content` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sale`
--

DROP TABLE IF EXISTS `sale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sale` (
  `sno` int NOT NULL AUTO_INCREMENT,
  `stitle` varchar(500) NOT NULL,
  `sdetail` text,
  `swriter` varchar(100) NOT NULL,
  `category` int DEFAULT NULL,
  `sreg_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `is_del` tinyint(1) DEFAULT '0',
  `is_end` varchar(10) DEFAULT 'false',
  `view_count` int DEFAULT '0',
  `favorite_count` int DEFAULT '0',
  `refund_message` text,
  `full_date` varchar(100) DEFAULT NULL,
  `shipping_exdate` varchar(100) DEFAULT NULL,
  `shipping_instructions` text,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`sno`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sale_favorite`
--

DROP TABLE IF EXISTS `sale_favorite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sale_favorite` (
  `sno` int DEFAULT NULL,
  `id` varchar(100) DEFAULT NULL,
  KEY `fk_sno` (`sno`),
  CONSTRAINT `fk_sno` FOREIGN KEY (`sno`) REFERENCES `sale` (`sno`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sale_img`
--

DROP TABLE IF EXISTS `sale_img`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sale_img` (
  `sno` int DEFAULT NULL,
  `uuid` varchar(256) NOT NULL,
  `save_dir` varchar(256) NOT NULL,
  `file_name` varchar(256) NOT NULL,
  `file_type` tinyint(1) DEFAULT '1',
  `file_size` int DEFAULT NULL,
  `reg_date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`),
  KEY `fk_sno_img` (`sno`),
  CONSTRAINT `fk_sno_img` FOREIGN KEY (`sno`) REFERENCES `sale` (`sno`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sale_orderer`
--

DROP TABLE IF EXISTS `sale_orderer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sale_orderer` (
  `sno` int DEFAULT NULL,
  `order_no` int NOT NULL AUTO_INCREMENT,
  `id` varchar(100) DEFAULT NULL,
  `nickname` varchar(100) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `phone_number` varchar(100) DEFAULT NULL,
  `sns_id` varchar(100) DEFAULT NULL,
  `refund_account` varchar(100) DEFAULT NULL,
  `refund_bank` varchar(100) DEFAULT NULL,
  `refund_account_holder` varchar(100) DEFAULT NULL,
  `order_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `order_status` varchar(100) DEFAULT 'PENDING_PAYMENT',
  PRIMARY KEY (`order_no`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sale_orderer_product`
--

DROP TABLE IF EXISTS `sale_orderer_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sale_orderer_product` (
  `sno` int DEFAULT NULL,
  `order_no` int NOT NULL,
  `id` varchar(100) DEFAULT NULL,
  `spno` int DEFAULT NULL,
  `spname` varchar(150) DEFAULT NULL,
  `spprice` int DEFAULT NULL,
  `count` int DEFAULT NULL,
  `order_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `total_price` int DEFAULT NULL,
  KEY `fk_order_no_product` (`order_no`),
  CONSTRAINT `fk_order_no_product` FOREIGN KEY (`order_no`) REFERENCES `sale_orderer` (`order_no`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sale_orderer_shipment`
--

DROP TABLE IF EXISTS `sale_orderer_shipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sale_orderer_shipment` (
  `sno` int DEFAULT NULL,
  `order_no` int DEFAULT NULL,
  `id` varchar(100) DEFAULT NULL,
  `recipient_name` varchar(100) DEFAULT NULL,
  `recipient_phone_number` varchar(100) DEFAULT NULL,
  `recipient_email` varchar(100) DEFAULT NULL,
  `recipient_postal_code` varchar(30) DEFAULT NULL,
  `recipient_detail_address` varchar(150) DEFAULT NULL,
  `shipping_method` varchar(100) DEFAULT NULL,
  `shipping_cost` int DEFAULT NULL,
  `shipping_note` text,
  `recipient_address` varchar(150) DEFAULT NULL,
  KEY `fk_order_no_shipment` (`order_no`),
  CONSTRAINT `fk_order_no_shipment` FOREIGN KEY (`order_no`) REFERENCES `sale_orderer` (`order_no`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sale_product`
--

DROP TABLE IF EXISTS `sale_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sale_product` (
  `spno` int NOT NULL AUTO_INCREMENT,
  `sno` int DEFAULT NULL,
  `spname` varchar(150) NOT NULL,
  `spprice` int NOT NULL,
  `max_order_quantity` int DEFAULT NULL,
  `stock` int DEFAULT NULL,
  PRIMARY KEY (`spno`)
) ENGINE=InnoDB AUTO_INCREMENT=184 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sale_product_img`
--

DROP TABLE IF EXISTS `sale_product_img`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sale_product_img` (
  `sno` int DEFAULT NULL,
  `spno` int DEFAULT NULL,
  `uuid` varchar(256) NOT NULL,
  `save_dir` varchar(256) NOT NULL,
  `file_name` varchar(256) NOT NULL,
  `file_type` tinyint(1) DEFAULT '1',
  `file_size` int DEFAULT NULL,
  `reg_date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`),
  KEY `spno` (`spno`),
  CONSTRAINT `sale_product_img_ibfk_1` FOREIGN KEY (`spno`) REFERENCES `sale_product` (`spno`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sale_seller`
--

DROP TABLE IF EXISTS `sale_seller`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sale_seller` (
  `sno` int DEFAULT NULL,
  `swriter` varchar(100) DEFAULT NULL,
  `account_number` varchar(100) DEFAULT NULL,
  `account_holder` varchar(100) DEFAULT NULL,
  `bank_name` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sale_shipment`
--

DROP TABLE IF EXISTS `sale_shipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sale_shipment` (
  `sno` int DEFAULT NULL,
  `shipping_method` varchar(100) DEFAULT NULL,
  `shipping_cost` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` varchar(100) NOT NULL,
  `pw` varchar(256) NOT NULL,
  `email` varchar(100) NOT NULL,
  `nickname` varchar(100) NOT NULL,
  `isseller` varchar(2) DEFAULT 'f',
  `regdate` datetime DEFAULT CURRENT_TIMESTAMP,
  `role` varchar(20) DEFAULT 'user',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_img`
--

DROP TABLE IF EXISTS `user_img`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_img` (
  `uuid` varchar(256) NOT NULL,
  `save_dir` varchar(256) NOT NULL,
  `file_name` varchar(256) NOT NULL,
  `file_type` tinyint(1) DEFAULT '1',
  `file_size` int DEFAULT NULL,
  `reg_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  KEY `id` (`id`),
  CONSTRAINT `user_img_ibfk_1` FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-27 13:35:10
