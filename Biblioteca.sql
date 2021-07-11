-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: localhost    Database: biblioteca
-- ------------------------------------------------------
-- Server version	8.0.23

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
-- Table structure for table `ejemplares`
--

DROP TABLE IF EXISTS `ejemplares`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ejemplares` (
  `idejemplar` int NOT NULL AUTO_INCREMENT,
  `idlibro` int NOT NULL,
  PRIMARY KEY (`idejemplar`),
  KEY `idlibro_fk_idx` (`idlibro`),
  CONSTRAINT `idlibro_fk` FOREIGN KEY (`idlibro`) REFERENCES `libros` (`idlibro`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ejemplares`
--

LOCK TABLES `ejemplares` WRITE;
/*!40000 ALTER TABLE `ejemplares` DISABLE KEYS */;
INSERT INTO `ejemplares` VALUES (1,3),(2,3),(3,3),(4,3),(5,3),(6,6),(7,6),(8,6),(9,6),(10,6),(11,6),(12,6),(14,7),(15,7),(16,7),(17,7),(18,7),(19,7),(20,7),(21,8),(22,8),(23,8),(24,8),(25,8),(26,9),(27,9),(28,9);
/*!40000 ALTER TABLE `ejemplares` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `libros`
--

DROP TABLE IF EXISTS `libros`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `libros` (
  `idlibro` int NOT NULL AUTO_INCREMENT,
  `isbn` varchar(20) NOT NULL,
  `titulo` varchar(25) NOT NULL,
  `nro_edicion` varchar(15) NOT NULL,
  `fecha_edicion` date NOT NULL,
  `maxdias_prestamo` int unsigned NOT NULL,
  PRIMARY KEY (`idlibro`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `libros`
--

LOCK TABLES `libros` WRITE;
/*!40000 ALTER TABLE `libros` DISABLE KEYS */;
INSERT INTO `libros` VALUES (3,'0-7645-2641-3','Harry Potter 1','Segunda','1998-01-01',14),(6,'0-7645-2893-5','Harry Potter 2','Tercera','2000-01-01',14),(7,'0-7645-3012-8','Harry Potter 3','Segunda','2001-02-28',14),(8,'1-2652-0235-1','Ready Player One','Primera','2007-05-13',7),(9,'3-6324-9901-5','Vivir para contarla','Quinta','2005-03-08',21);
/*!40000 ALTER TABLE `libros` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lineas_prestamo`
--

DROP TABLE IF EXISTS `lineas_prestamo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lineas_prestamo` (
  `idlinea` int NOT NULL AUTO_INCREMENT,
  `fecha_devolucion` date DEFAULT NULL,
  `idejemplar` int NOT NULL,
  `idprestamo` int NOT NULL,
  PRIMARY KEY (`idlinea`),
  KEY `idprestamo_fk_idx` (`idprestamo`),
  KEY `idejemplar_fk_idx` (`idejemplar`),
  CONSTRAINT `idejemplar_fk` FOREIGN KEY (`idejemplar`) REFERENCES `ejemplares` (`idejemplar`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `idprestamo_fk` FOREIGN KEY (`idprestamo`) REFERENCES `prestamos` (`idprestamo`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lineas_prestamo`
--

LOCK TABLES `lineas_prestamo` WRITE;
/*!40000 ALTER TABLE `lineas_prestamo` DISABLE KEYS */;
INSERT INTO `lineas_prestamo` VALUES (7,'2021-07-11',1,3),(8,'2021-07-11',25,5);
/*!40000 ALTER TABLE `lineas_prestamo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `politica_prestamo`
--

DROP TABLE IF EXISTS `politica_prestamo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `politica_prestamo` (
  `fecha` date NOT NULL,
  `cantmax_librospend` int unsigned NOT NULL,
  `idpolitica` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`idpolitica`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `politica_prestamo`
--

LOCK TABLES `politica_prestamo` WRITE;
/*!40000 ALTER TABLE `politica_prestamo` DISABLE KEYS */;
INSERT INTO `politica_prestamo` VALUES ('2021-07-01',3,1),('2021-06-02',4,2),('2021-05-01',3,3),('2021-07-03',5,4),('2021-07-04',3,5);
/*!40000 ALTER TABLE `politica_prestamo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `politica_sancion`
--

DROP TABLE IF EXISTS `politica_sancion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `politica_sancion` (
  `idpolitica` int NOT NULL AUTO_INCREMENT,
  `dias_desde` int NOT NULL,
  `dias_hasta` int DEFAULT NULL,
  `dias_sancion` int NOT NULL,
  PRIMARY KEY (`idpolitica`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `politica_sancion`
--

LOCK TABLES `politica_sancion` WRITE;
/*!40000 ALTER TABLE `politica_sancion` DISABLE KEYS */;
INSERT INTO `politica_sancion` VALUES (1,1,3,7),(2,4,6,14),(3,7,50,21);
/*!40000 ALTER TABLE `politica_sancion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prestamos`
--

DROP TABLE IF EXISTS `prestamos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prestamos` (
  `idprestamo` int NOT NULL AUTO_INCREMENT,
  `fecha_prestamo` date NOT NULL,
  `hora_prestamo` time NOT NULL,
  `dias` int NOT NULL,
  `idsocio` int NOT NULL,
  PRIMARY KEY (`idprestamo`),
  KEY `idsocio_fk2_idx` (`idsocio`),
  CONSTRAINT `idsocio_fk2` FOREIGN KEY (`idsocio`) REFERENCES `socios` (`idsocio`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prestamos`
--

LOCK TABLES `prestamos` WRITE;
/*!40000 ALTER TABLE `prestamos` DISABLE KEYS */;
INSERT INTO `prestamos` VALUES (3,'2021-07-08','23:16:45',14,1),(5,'2021-06-23','15:00:00',14,3);
/*!40000 ALTER TABLE `prestamos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sanciones`
--

DROP TABLE IF EXISTS `sanciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sanciones` (
  `idsancion` int NOT NULL AUTO_INCREMENT,
  `fecha_sancion` date NOT NULL,
  `dias_sancion` int NOT NULL,
  `idsocio` int NOT NULL,
  PRIMARY KEY (`idsancion`),
  KEY `idsocio_fk_idx` (`idsocio`),
  CONSTRAINT `idsocio_fk` FOREIGN KEY (`idsocio`) REFERENCES `socios` (`idsocio`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sanciones`
--

LOCK TABLES `sanciones` WRITE;
/*!40000 ALTER TABLE `sanciones` DISABLE KEYS */;
INSERT INTO `sanciones` VALUES (5,'2021-07-11',10,3),(6,'2021-07-11',21,1);
/*!40000 ALTER TABLE `sanciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `socios`
--

DROP TABLE IF EXISTS `socios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `socios` (
  `idsocio` int NOT NULL AUTO_INCREMENT,
  `apellido` varchar(30) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `mail` varchar(30) NOT NULL,
  `domicilio` varchar(30) NOT NULL,
  `telefono` varchar(15) NOT NULL,
  PRIMARY KEY (`idsocio`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `socios`
--

LOCK TABLES `socios` WRITE;
/*!40000 ALTER TABLE `socios` DISABLE KEYS */;
INSERT INTO `socios` VALUES (1,' Echecury',' Sebastian',' prueba@gmail.com','Urquiza 369','3416410025'),(3,'James','LeBron','theking@gmail.com','LA 456','4566666'),(4,'Booker','Devin','devinb@gmail.com','phoenix 789','7899999'),(5,'Butler','Jimmy','jimmybuckets@gmail.com','Miami 148','1488888');
/*!40000 ALTER TABLE `socios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-07-11 20:27:53
