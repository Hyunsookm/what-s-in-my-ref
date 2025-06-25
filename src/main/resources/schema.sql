-- MySQL dump 10.13  Distrib 8.0.38, for macos14 (arm64)
--
-- Host: 127.0.0.1    Database: wimr
-- ------------------------------------------------------
-- Server version	8.0.38

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
-- Table structure for table `board`
--

DROP TABLE IF EXISTS `board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `board` (
  `bno` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `content` varchar(2000) NOT NULL,
  `nickname` varchar(30) NOT NULL,
  `time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `viewcount` int DEFAULT '0',
  `likes` int DEFAULT '0',
  `created_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`bno`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `board_comment`
--

DROP TABLE IF EXISTS `board_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `board_comment` (
  `bno` int NOT NULL,
  `cmtno` int NOT NULL AUTO_INCREMENT,
  `time` datetime(6) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cmtno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manual_img`
--

DROP TABLE IF EXISTS `manual_img`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `manual_img` (
  `recipe_recipeno` int NOT NULL,
  `manual_img` varchar(255) DEFAULT NULL,
  `manual_img_key` varchar(255) NOT NULL,
  PRIMARY KEY (`recipe_recipeno`,`manual_img_key`),
  CONSTRAINT `FKaa1dxv7fbprs5cd651m2g0an7` FOREIGN KEY (`recipe_recipeno`) REFERENCES `recipe` (`recipeno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `member_no` int NOT NULL AUTO_INCREMENT,
  `member_email` varchar(255) NOT NULL,
  `member_id` varchar(255) NOT NULL,
  `member_nick` varchar(255) NOT NULL,
  `member_pw` varchar(255) NOT NULL,
  PRIMARY KEY (`member_no`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `membercmt`
--

DROP TABLE IF EXISTS `membercmt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `membercmt` (
  `cmtno` int NOT NULL AUTO_INCREMENT,
  `bno` int DEFAULT NULL,
  `comment` text,
  `nickname` varchar(10) DEFAULT NULL,
  `time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`cmtno`),
  CONSTRAINT `membercmt_ibfk_1` FOREIGN KEY (`cmtno`) REFERENCES `board` (`bno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `my_board`
--

DROP TABLE IF EXISTS `my_board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `my_board` (
  `bno` int NOT NULL,
  `member_no` int NOT NULL,
  PRIMARY KEY (`bno`,`member_no`),
  KEY `FKs2un8uokbiv1348yovxsehghm` (`member_no`),
  CONSTRAINT `FK26axtu1qqngc279bduufrp73w` FOREIGN KEY (`bno`) REFERENCES `board` (`bno`),
  CONSTRAINT `FKs2un8uokbiv1348yovxsehghm` FOREIGN KEY (`member_no`) REFERENCES `member` (`member_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `my_recipe`
--

DROP TABLE IF EXISTS `my_recipe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `my_recipe` (
  `member_no` int NOT NULL,
  `recipeno` int NOT NULL,
  PRIMARY KEY (`member_no`,`recipeno`),
  KEY `FKesh57wd8npj43j4so5fkuy0ax` (`recipeno`),
  CONSTRAINT `FKesh57wd8npj43j4so5fkuy0ax` FOREIGN KEY (`recipeno`) REFERENCES `recipe` (`recipeno`),
  CONSTRAINT `FKhjxq6fxfwpnlgs3jbm3ugbxut` FOREIGN KEY (`member_no`) REFERENCES `member` (`member_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `personal_recipe`
--

DROP TABLE IF EXISTS `personal_recipe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `personal_recipe` (
  `recipeno` int NOT NULL,
  `view_count` int NOT NULL,
  `time` datetime(6) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `others` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`recipeno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `recipe`
--

DROP TABLE IF EXISTS `recipe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recipe` (
  `recipeno` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `nickname` varchar(10) NOT NULL DEFAULT 'aapl',
  `ingredient` varchar(300) DEFAULT NULL,
  `nutrition` varchar(300) DEFAULT NULL,
  `content` text,
  `likes` int DEFAULT '0',
  `calories` varchar(255) DEFAULT NULL,
  `carbohydrates` varchar(255) DEFAULT NULL,
  `fat` varchar(255) DEFAULT NULL,
  `ingredients` tinytext,
  `protein` varchar(255) DEFAULT NULL,
  `reduction_tip` varchar(255) DEFAULT NULL,
  `sodium` varchar(255) DEFAULT NULL,
  `likecount` int NOT NULL DEFAULT '0',
  `picture` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`recipeno`)
) ENGINE=InnoDB AUTO_INCREMENT=1013 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `recipe_cmt`
--

DROP TABLE IF EXISTS `recipe_cmt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recipe_cmt` (
  `cmtno` int NOT NULL AUTO_INCREMENT,
  `rno` int NOT NULL,
  `time` datetime(6) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cmtno`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `recipe_comment`
--

DROP TABLE IF EXISTS `recipe_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recipe_comment` (
  `cmtno` int NOT NULL AUTO_INCREMENT,
  `rno` int NOT NULL,
  `time` datetime(6) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cmtno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `recipe_likes`
--

DROP TABLE IF EXISTS `recipe_likes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recipe_likes` (
  `member_no` int NOT NULL,
  `recipe_no` int NOT NULL,
  PRIMARY KEY (`member_no`,`recipe_no`),
  KEY `FKa206c6wr3yl435e3j9nhdvr5k` (`recipe_no`),
  CONSTRAINT `FK2kip5eqoppeugd5fo5o9hnqgy` FOREIGN KEY (`member_no`) REFERENCES `member` (`member_no`),
  CONSTRAINT `FKa206c6wr3yl435e3j9nhdvr5k` FOREIGN KEY (`recipe_no`) REFERENCES `recipe` (`recipeno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `recipe_manual`
--

DROP TABLE IF EXISTS `recipe_manual`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recipe_manual` (
  `recipe_recipeno` int NOT NULL,
  `manual` varchar(255) DEFAULT NULL,
  `manual_key` varchar(255) NOT NULL,
  PRIMARY KEY (`recipe_recipeno`,`manual_key`),
  CONSTRAINT `FK6dhnofr1ugdequ704yjxea8ya` FOREIGN KEY (`recipe_recipeno`) REFERENCES `recipe` (`recipeno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `recipe_manual_img`
--

DROP TABLE IF EXISTS `recipe_manual_img`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recipe_manual_img` (
  `recipe_recipeno` int NOT NULL,
  `manual_img` varchar(255) DEFAULT NULL,
  `manual_img_key` varchar(255) NOT NULL,
  PRIMARY KEY (`recipe_recipeno`,`manual_img_key`),
  CONSTRAINT `FK5mwgtautkatrxh92m377smlgc` FOREIGN KEY (`recipe_recipeno`) REFERENCES `recipe` (`recipeno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `recipecmt`
--

DROP TABLE IF EXISTS `recipecmt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recipecmt` (
  `cmtno` int NOT NULL AUTO_INCREMENT,
  `rno` int DEFAULT NULL,
  `comment` text,
  `nickname` varchar(10) DEFAULT NULL,
  `time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`cmtno`),
  CONSTRAINT `recipecmt_ibfk_1` FOREIGN KEY (`cmtno`) REFERENCES `recipe` (`recipeno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `scrap`
--

DROP TABLE IF EXISTS `scrap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `scrap` (
  `member_no` int NOT NULL,
  `recipe_no` int NOT NULL,
  PRIMARY KEY (`member_no`,`recipe_no`),
  KEY `FKdb9g9snhrusjaxgs6dghq3g5n` (`recipe_no`),
  CONSTRAINT `FKdb9g9snhrusjaxgs6dghq3g5n` FOREIGN KEY (`recipe_no`) REFERENCES `recipe` (`recipeno`),
  CONSTRAINT `FKgfdw8e7ivc76c6musfpd9e3ut` FOREIGN KEY (`member_no`) REFERENCES `member` (`member_no`)
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

-- Dump completed on 2024-07-18 19:58:56
