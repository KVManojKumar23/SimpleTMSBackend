CREATE DATABASE  IF NOT EXISTS `task_management` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `task_management`;
-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: task_management
-- ------------------------------------------------------
-- Server version	8.0.40

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
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employees` (
  `emp_id` int NOT NULL AUTO_INCREMENT,
  `exit_date` date DEFAULT NULL,
  `joining_date` date NOT NULL,
  `org_id` int NOT NULL,
  `user_id` int NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `employee_id` varchar(50) DEFAULT NULL,
  `reporting_to` varchar(50) DEFAULT NULL,
  `department` varchar(100) DEFAULT NULL,
  `designation` varchar(100) NOT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `status` enum('ACTIVE','INACTIVE','ON_LEAVE','RESIGNED','TERMINATED') DEFAULT NULL,
  PRIMARY KEY (`emp_id`),
  UNIQUE KEY `UKj2dmgsma6pont6kf7nic9elpd` (`user_id`),
  KEY `idx_employee_user` (`user_id`),
  KEY `idx_employee_org` (`org_id`),
  KEY `idx_employee_status` (`status`),
  CONSTRAINT `FK69x3vjuy1t5p18a5llb8h2fjx` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FKbxfuftcn1xld02fbcxk8xbuty` FOREIGN KEY (`org_id`) REFERENCES `organizations` (`org_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organizations`
--

DROP TABLE IF EXISTS `organizations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `organizations` (
  `org_id` int NOT NULL AUTO_INCREMENT,
  `status` bit(1) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `phone_number` varchar(15) NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `industry_type` varchar(50) NOT NULL,
  `updated_by` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `org_name` varchar(100) NOT NULL,
  `website` varchar(150) DEFAULT NULL,
  `address` varchar(255) NOT NULL,
  PRIMARY KEY (`org_id`),
  UNIQUE KEY `UKpe8sgvd49hsasoq2kqav6nhkv` (`email`),
  UNIQUE KEY `UKit9oxa0rmaumshumc3m46lvam` (`org_name`),
  KEY `idx_org_name` (`org_name`),
  KEY `idx_org_email` (`email`),
  KEY `idx_org_phone` (`phone_number`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organizations`
--

LOCK TABLES `organizations` WRITE;
/*!40000 ALTER TABLE `organizations` DISABLE KEYS */;
INSERT INTO `organizations` VALUES (1,_binary '','2025-04-30 11:55:04.975301','2025-04-30 11:55:04.975301','9876543210','admin','IT Services','admin','contact@techspark.com','TechSpark Solutions','https://www.techspark.com','123 Innovation Road, Bangalore'),(2,_binary '','2025-04-30 11:55:05.061034','2025-04-30 11:55:05.061034','9123456789','admin','Healthcare','admin','support@medicorelabs.com','MediCore Labs','https://www.medicorelabs.com','45 Health Street, Hyderabad'),(3,_binary '','2025-04-30 11:55:05.062639','2025-04-30 11:55:05.062639','9988776655','admin','Renewable Energy','admin','info@greengrid.com','GreenGrid Energy','https://www.greengrid.com','789 Eco Park, Pune'),(4,_binary '','2025-04-30 11:55:05.070164','2025-04-30 11:55:05.070164','9786543210','admin','Education','admin','hello@eduleap.com','EduLeap Academy','https://www.eduleap.com','88 Knowledge Ave, Delhi'),(5,_binary '','2025-04-30 11:55:05.070164','2025-04-30 11:55:05.070164','9345612789','admin','Finance','admin','services@fingenius.com','FinGenius Pvt Ltd','https://www.fingenius.com','66 Finance Street, Mumbai');
/*!40000 ALTER TABLE `organizations` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `project_employees`
--

DROP TABLE IF EXISTS `project_employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project_employees` (
  `project_id` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`project_id`,`user_id`),
  KEY `FKrm9lh2ikv9y64wv9r162g7121` (`user_id`),
  CONSTRAINT `FKrm9lh2ikv9y64wv9r162g7121` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FKt9kxewjtx1iqoxwasi3ma5e0t` FOREIGN KEY (`project_id`) REFERENCES `projects` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_employees`
--

LOCK TABLES `project_employees` WRITE;
/*!40000 ALTER TABLE `project_employees` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_employees` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `project_managers`
--

DROP TABLE IF EXISTS `project_managers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project_managers` (
  `project_id` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`project_id`,`user_id`),
  KEY `FKskrbaq5tnhvttfmdfqdt0mff6` (`user_id`),
  CONSTRAINT `FKmt17f0hw6vgjy4ffyxqn6vksv` FOREIGN KEY (`project_id`) REFERENCES `projects` (`project_id`),
  CONSTRAINT `FKskrbaq5tnhvttfmdfqdt0mff6` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_managers`
--

LOCK TABLES `project_managers` WRITE;
/*!40000 ALTER TABLE `project_managers` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_managers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `projects`
--

DROP TABLE IF EXISTS `projects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `projects` (
  `created_by` int NOT NULL,
  `is_deleted` bit(1) NOT NULL,
  `org_id` int NOT NULL,
  `project_id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `end_date` datetime(6) DEFAULT NULL,
  `start_date` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `project_name` varchar(100) NOT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `status` enum('ACTIVE','CANCELLED','COMPLETED','ON_HOLD') NOT NULL,
  PRIMARY KEY (`project_id`),
  KEY `FKf1ph00os6khfle3ub9b50x594` (`created_by`),
  KEY `FKox6tplayrlog0orf9t1i9q4yn` (`org_id`),
  CONSTRAINT `FKf1ph00os6khfle3ub9b50x594` FOREIGN KEY (`created_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FKox6tplayrlog0orf9t1i9q4yn` FOREIGN KEY (`org_id`) REFERENCES `organizations` (`org_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projects`
--

LOCK TABLES `projects` WRITE;
/*!40000 ALTER TABLE `projects` DISABLE KEYS */;
/*!40000 ALTER TABLE `projects` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `role_name` enum('ADMIN','EMPLOYEE','HR','MANAGER','ORGANIZATION','USER') NOT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `UK716hgxp60ym1lifrdgp67xt5k` (`role_name`),
  KEY `idx_role_name` (`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'2025-04-03 13:24:24.787123','2025-04-03 13:24:24.787123','Department admin','ADMIN'),(2,'2025-04-03 13:25:34.515106','2025-04-03 13:25:34.515106','Organization administrator','ORGANIZATION'),(3,'2025-04-03 13:26:06.309662','2025-04-03 13:26:06.309662','Human Resources','HR'),(4,'2025-04-03 13:26:28.436005','2025-04-03 13:26:28.436005','Team manager','MANAGER'),(5,'2025-04-03 13:27:14.302569','2025-04-03 13:27:14.302569','Basic user access','USER');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sprint_tasks`
--

DROP TABLE IF EXISTS `sprint_tasks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sprint_tasks` (
  `assignee_id` int DEFAULT NULL,
  `hours_actual` int DEFAULT NULL,
  `hours_estimate` int DEFAULT NULL,
  `sprint_id` int NOT NULL,
  `story_points` int NOT NULL,
  `version` int DEFAULT NULL,
  `completed_at` datetime(6) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `started_at` datetime(6) DEFAULT NULL,
  `task_id` bigint NOT NULL AUTO_INCREMENT,
  `ticket_number` varchar(50) DEFAULT NULL,
  `title` varchar(200) NOT NULL,
  `pull_request_url` varchar(500) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `priority` enum('CRITICAL','HIGH','LOW','MEDIUM') NOT NULL,
  `status` enum('BLOCKED','CODE_REVIEW','DONE','IN_PROGRESS','QA_TESTING','TODO') NOT NULL,
  `type` enum('BUG','EPIC','STORY','SUBTASK','TASK') NOT NULL,
  PRIMARY KEY (`task_id`),
  KEY `idx_task_sprint` (`sprint_id`),
  KEY `idx_task_status` (`status`),
  KEY `idx_task_assignee` (`assignee_id`),
  CONSTRAINT `FKmv9v26krrcdvqpqxqylh7pbuj` FOREIGN KEY (`sprint_id`) REFERENCES `sprints` (`sprint_id`),
  CONSTRAINT `FKnxtgvajvktisir2bkrfajomvs` FOREIGN KEY (`assignee_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sprint_tasks`
--

LOCK TABLES `sprint_tasks` WRITE;
/*!40000 ALTER TABLE `sprint_tasks` DISABLE KEYS */;
/*!40000 ALTER TABLE `sprint_tasks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sprints`
--

DROP TABLE IF EXISTS `sprints`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sprints` (
  `end_date` date NOT NULL,
  `project_id` int NOT NULL,
  `sprint_id` int NOT NULL AUTO_INCREMENT,
  `start_date` date NOT NULL,
  `velocity` int DEFAULT NULL,
  `completed_at` datetime(6) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `sprint_name` varchar(100) NOT NULL,
  `goal` varchar(500) DEFAULT NULL,
  `status` enum('CANCELLED','COMPLETED','IN_PROGRESS','PLANNED') NOT NULL,
  PRIMARY KEY (`sprint_id`),
  KEY `idx_sprint_project` (`project_id`),
  KEY `idx_sprint_dates` (`start_date`,`end_date`),
  KEY `idx_sprint_status` (`status`),
  CONSTRAINT `FKke5a9e380ibc0xugykeqaktp4` FOREIGN KEY (`project_id`) REFERENCES `projects` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sprints`
--

LOCK TABLES `sprints` WRITE;
/*!40000 ALTER TABLE `sprints` DISABLE KEYS */;
/*!40000 ALTER TABLE `sprints` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `active` bit(1) NOT NULL,
  `date_of_birth` date DEFAULT NULL,
  `org_id` int DEFAULT NULL,
  `role_id` int DEFAULT NULL,
  `user_id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `last_login` datetime(6) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `reset_token_expiry` datetime(6) DEFAULT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `password` varchar(60) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `full_name` varchar(100) DEFAULT NULL,
  `reset_token` varchar(100) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `gender` enum('FEMALE','MALE','OTHER') DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UK9q63snka3mdh91as4io72espi` (`phone_number`),
  KEY `idx_user_email` (`email`),
  KEY `idx_user_phone` (`phone_number`),
  KEY `FK902wn47cndp7hjhfvvb48brg` (`org_id`),
  KEY `FKp56c1712k691lhsyewcssf40f` (`role_id`),
  CONSTRAINT `FK902wn47cndp7hjhfvvb48brg` FOREIGN KEY (`org_id`) REFERENCES `organizations` (`org_id`),
  CONSTRAINT `FKp56c1712k691lhsyewcssf40f` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-30 17:34:58
