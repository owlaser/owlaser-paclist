-- MySQL dump 10.13  Distrib 5.7.19, for macos10.12 (x86_64)
--
-- Host: 127.0.0.1    Database: OWLASER
-- ------------------------------------------------------
-- Server version	5.7.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `maven_dependency`
--

DROP TABLE IF EXISTS `maven_dependency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `maven_dependency` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` varchar(255) NOT NULL,
  `artifact_id` varchar(255) NOT NULL,
  `popular_version` varchar(63) NOT NULL,
  `stable_version` varchar(63) NOT NULL,
  `license` varchar(64) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `maven_dependency`
--

LOCK TABLES `maven_dependency` WRITE;
/*!40000 ALTER TABLE `maven_dependency` DISABLE KEYS */;
INSERT INTO `maven_dependency` VALUES (1,'org.mybatis.spring.boot','mybatis-spring-boot-starter','1.3.2','2.1.1','Apache 2.0'),(2,'dom4j','dom4j','1.6.1','1.6.1','BSD'),(3,'org.jsoup','jsoup','1.11.3','1.12.1','MIT'),(7,'com.github.pagehelper','pagehelper-spring-boot-starter','1.2.10','1.2.12','MIT'),(8,'org.apache.shiro','shiro-spring-boot-web-starter','1.4.0','1.4.2','Apache 2.0'),(9,'org.hibernate.validator','hibernate-validator','6.0.16.Final','6.1.0.Final','Apache 2.0'),(10,'mysql','mysql-connector-java','5.1.38','8.0.18','GPL 2.0'),(11,'com.alibaba','druid-spring-boot-starter','1.1.10','1.1.21','Apache 2.0'),(12,'com.github.binarywang','weixin-java-pay','3.5.0','3.6.0','Apache 2.0'),(13,'com.github.binarywang','weixin-java-miniapp','3.5.0','3.6.0','Apache 2.0'),(14,'com.github.qcloudsms','qcloudsms','1.0.6','1.0.6','Apache 2.0'),(15,'com.qcloud','cos_api','5.5.9','5.6.9',''),(16,'com.aliyun.oss','aliyun-sdk-oss','2.8.3','3.8.0',''),(17,'com.qiniu','qiniu-java-sdk','7.2.17','7.2.27','MIT'),(18,'org.springframework.boot','spring-boot-starter-json','2.1.4.RELEASE','2.2.1.RELEASE','Apache 2.0'),(19,'org.springframework.boot','spring-boot-starter-mail','2.1.3.RELEASE','2.2.1.RELEASE','Apache 2.0'),(20,'io.springfox','springfox-swagger2','2.9.2','2.9.2','Apache 2.0'),(21,'io.springfox','springfox-swagger-ui','2.9.2','2.9.2','Apache 2.0'),(22,'org.powermock','powermock-api-mockito','1.6.5','1.7.4','Apache 2.0'),(23,'org.powermock','powermock-module-junit4','1.6.5','2.0.4','Apache 2.0'),(24,'org.mockito','mockito-core','1.10.19','3.1.0','MIT');
/*!40000 ALTER TABLE `maven_dependency` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-11-25 19:42:25
