-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: test8
-- ------------------------------------------------------
-- Server version	8.0.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `egzemplarze`
--

DROP TABLE IF EXISTS `egzemplarze`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `egzemplarze` (
  `id_e` int NOT NULL AUTO_INCREMENT,
  `id_ogrodka` int NOT NULL,
  `id_rosliny` int NOT NULL,
  `miejsce` varchar(100) NOT NULL,
  `status` enum('NIE ZASADZONE','ZASADZONE','DO ZBIORU','ZEBRANE') NOT NULL DEFAULT 'NIE ZASADZONE',
  `data_zmiany_statusu` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_e`),
  UNIQUE KEY `id_e` (`id_e`),
  UNIQUE KEY `miejsce_w_ogrodku` (`id_ogrodka`,`miejsce`),
  KEY `id_rosliny` (`id_rosliny`),
  CONSTRAINT `egzemplarze_ibfk_1` FOREIGN KEY (`id_rosliny`) REFERENCES `rosliny` (`id_r`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `egzemplarze`
--

LOCK TABLES `egzemplarze` WRITE;
/*!40000 ALTER TABLE `egzemplarze` DISABLE KEYS */;
INSERT INTO `egzemplarze` VALUES (1,1,1,'A1','NIE ZASADZONE','2020-05-03 12:10:10'),(2,1,2,'A2','NIE ZASADZONE','2020-05-03 12:10:10'),(3,2,1,'A1','NIE ZASADZONE','2020-05-03 12:10:10'),(4,2,2,'A2','NIE ZASADZONE','2020-05-03 12:10:10'),(8,2,2,'A3','NIE ZASADZONE','2020-05-03 12:10:10'),(10,1,1,'A3','NIE ZASADZONE','2020-05-03 19:11:47'),(15,1,1,'A7','NIE ZASADZONE','2020-05-03 21:10:33'),(17,1,3,'A8','NIE ZASADZONE','2020-05-04 13:52:57'),(18,1,1,'a10','NIE ZASADZONE','2020-05-05 12:10:58');
/*!40000 ALTER TABLE `egzemplarze` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = cp852 */ ;
/*!50003 SET character_set_results = cp852 */ ;
/*!50003 SET collation_connection  = cp852_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `dodaj_powiadomienie_zasiania` AFTER INSERT ON `egzemplarze` FOR EACH ROW IF NEW.status = 'NIE ZASADZONE' THEN SET @data_koncowa_zasiana = (SELECT okres_siewu_koniec FROM rosliny WHERE rosliny.id_r = NEW.id_rosliny); SET @data_poczatkowa_zasiana = (SELECT okres_siewu_poczatek FROM rosliny WHERE rosliny.id_r = NEW.id_rosliny); INSERT INTO powiadomienia(id_egzemplarza,id_ogrodka,tresc,data_powiadomienia) VALUES (NEW.id_e, NEW.id_ogrodka,'Nie zapomnij mnie zasadzi�!',@data_koncowa_zasiana), (NEW.id_e, NEW.id_ogrodka,'Mo�esz mnie zasadzi�!',@data_poczatkowa_zasiana);  ELSEIF NEW.status = 'ZASADZONE' THEN   SET @data_poczatkowa_zbioru = (SELECT okres_zbioru_poczatek FROM rosliny WHERE rosliny.id_r = NEW.id_rosliny);  SET @data_koncowa_zbioru = (SELECT okres_zbioru_koniec FROM rosliny WHERE rosliny.id_r = NEW.id_rosliny); INSERT INTO powiadomienia(id_egzemplarza,id_ogrodka,tresc,data_powiadomienia) VALUES (NEW.id_e, NEW.id_ogrodka,'Gotowe do zebrania!',@data_poczatkowa_zbioru), (NEW.id_e, NEW.id_ogrodka,'Nie zapomnij mnie zebra�!',@data_poczatkowa_zbioru); END IF */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = cp852 */ ;
/*!50003 SET character_set_results = cp852 */ ;
/*!50003 SET collation_connection  = cp852_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `zmien_powiadomienie_status` AFTER UPDATE ON `egzemplarze` FOR EACH ROW IF NEW.status = 'NIE ZASADZONE' THEN DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Nie zapomnij mnie zebra�!'; DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Gotowe do zebrania!'; DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Nie zapomnij mnie zasadzi�!'; DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Mo�esz mnie zasadzi�!'; SET @data_koncowa_zasiana = (SELECT okres_siewu_koniec FROM rosliny WHERE rosliny.id_r = NEW.id_rosliny);  SET @data_poczatkowa_zasiana = (SELECT okres_siewu_poczatek FROM rosliny WHERE rosliny.id_r = NEW.id_rosliny); INSERT INTO powiadomienia(id_egzemplarza,id_ogrodka,tresc,data_powiadomienia) VALUES (NEW.id_e, NEW.id_ogrodka,'Nie zapomnij mnie zasadzi�!',@data_koncowa_zasiana), (NEW.id_e, NEW.id_ogrodka,'Mo�esz mnie zasadzi�!',@data_poczatkowa_zasiana);  ELSEIF NEW.status = 'ZASADZONE' THEN  DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Nie zapomnij mnie zebra�!'; DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Gotowe do zebrania!'; DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Nie zapomnij mnie zasadzi�!'; DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Mo�esz mnie zasadzi�!';SET @data_poczatkowa_zbioru = (SELECT okres_zbioru_poczatek FROM rosliny WHERE rosliny.id_r = NEW.id_rosliny); INSERT INTO powiadomienia(id_egzemplarza, id_ogrodka,tresc,data_powiadomienia) VALUES (NEW.id_e,NEW.id_ogrodka,'Gotowe do zebrania!',@data_poczatkowa_zbioru);  ELSEIF NEW.status = 'DO ZBIORU' THEN DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Nie zapomnij mnie zebra�!'; DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Gotowe do zebrania!'; DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Nie zapomnij mnie zasadzi�!'; DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Mo�esz mnie zasadzi�!'; SET @data_koncowa_zabioru = (SELECT okres_zbioru_koniec FROM rosliny WHERE rosliny.id_r = NEW.id_rosliny); INSERT INTO powiadomienia(id_egzemplarza, id_ogrodka,tresc,data_powiadomienia) VALUES (NEW.id_e,NEW.id_ogrodka,'Nie zapomnij mnie zebra�!',@data_koncowa_zabioru);  ELSEIF NEW.status = 'ZEBRANE' THEN  DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Nie zapomnij mnie zebra�!'; DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Gotowe do zebrania!'; DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Nie zapomnij mnie zasadzi�!'; DELETE FROM powiadomienia where id_egzemplarza = NEW.id_e AND tresc = 'Mo�esz mnie zasadzi�!';END IF */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `obserwacje`
--

DROP TABLE IF EXISTS `obserwacje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `obserwacje` (
  `id_o` int NOT NULL AUTO_INCREMENT,
  `id_uzytkownika` int NOT NULL,
  `id_egzemplarza` int NOT NULL,
  `tresc` varchar(1000) NOT NULL,
  `data_obserwacji` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_o`),
  UNIQUE KEY `id_o` (`id_o`),
  KEY `id_egzemplarza` (`id_egzemplarza`),
  KEY `id_uzytkownika` (`id_uzytkownika`),
  CONSTRAINT `obserwacje_ibfk_1` FOREIGN KEY (`id_egzemplarza`) REFERENCES `egzemplarze` (`id_e`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `obserwacje_ibfk_2` FOREIGN KEY (`id_uzytkownika`) REFERENCES `uzytkownicy` (`id_u`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `obserwacje`
--

LOCK TABLES `obserwacje` WRITE;
/*!40000 ALTER TABLE `obserwacje` DISABLE KEYS */;
INSERT INTO `obserwacje` VALUES (1,1,1,'Marchew rośnie książkowo','2020-05-03 12:05:48'),(2,1,1,'Faza wzrostu: początkowa ','2020-05-03 12:24:31'),(9,2,3,'faza wzrostu: początkowa','2020-05-03 16:45:18'),(12,1,10,'Faza wzrostu: początkowa ','2020-05-05 13:26:27'),(19,1,17,'Faza wzrostu: początkowa ','2020-05-05 13:27:46'),(20,1,18,'Faza wzrostu: początkowa ','2020-05-05 13:27:51');
/*!40000 ALTER TABLE `obserwacje` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ogrodki`
--

DROP TABLE IF EXISTS `ogrodki`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ogrodki` (
  `id_og` int NOT NULL AUTO_INCREMENT,
  `id_uzytkownika` int NOT NULL,
  `nazwa` varchar(100) NOT NULL,
  `adres` varchar(100) NOT NULL,
  PRIMARY KEY (`id_og`),
  UNIQUE KEY `id_og` (`id_og`),
  KEY `id_uzytkownika` (`id_uzytkownika`),
  CONSTRAINT `ogrodki_ibfk_1` FOREIGN KEY (`id_uzytkownika`) REFERENCES `uzytkownicy` (`id_u`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ogrodki`
--

LOCK TABLES `ogrodki` WRITE;
/*!40000 ALTER TABLE `ogrodki` DISABLE KEYS */;
INSERT INTO `ogrodki` VALUES (1,1,'Ogródek warzywny','ul. Kolejowa 22 Borków'),(2,2,'Ogródek1','ul. Majowa 13 Malczyce'),(3,1,'Ogródek owocowy','ul.Kolejowa 22 Borków'),(5,5,'Ogródek A4','ul. Brzoskwiniowa 6 Wrocław'),(8,1,'Ogrodek mamy','ul. Wrocławska 2 Wrocław');
/*!40000 ALTER TABLE `ogrodki` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `powiadomienia`
--

DROP TABLE IF EXISTS `powiadomienia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `powiadomienia` (
  `id_p` int NOT NULL AUTO_INCREMENT,
  `id_egzemplarza` int DEFAULT NULL,
  `id_ogrodka` int DEFAULT NULL,
  `tresc` varchar(400) NOT NULL,
  `data_powiadomienia` datetime NOT NULL,
  PRIMARY KEY (`id_p`),
  UNIQUE KEY `id_p` (`id_p`),
  KEY `id_ogrodka` (`id_ogrodka`),
  KEY `id_egzemplarza` (`id_egzemplarza`),
  CONSTRAINT `powiadomienia_ibfk_1` FOREIGN KEY (`id_ogrodka`) REFERENCES `ogrodki` (`id_og`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `powiadomienia_ibfk_2` FOREIGN KEY (`id_egzemplarza`) REFERENCES `egzemplarze` (`id_e`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `powiadomienia`
--

LOCK TABLES `powiadomienia` WRITE;
/*!40000 ALTER TABLE `powiadomienia` DISABLE KEYS */;
INSERT INTO `powiadomienia` VALUES (2,NULL,1,' Przekop ogródek','2020-03-01 12:00:00'),(54,1,1,'Nie zapomnij mnie zasadzić!','2020-04-15 00:00:00'),(55,1,1,'Możesz mnie zasadzić!','2020-03-15 00:00:00'),(56,2,1,'Nie zapomnij mnie zasadzić!','2020-05-30 00:00:00'),(57,2,1,'Możesz mnie zasadzić!','2020-03-01 00:00:00'),(58,3,2,'Nie zapomnij mnie zasadzić!','2020-04-15 00:00:00'),(59,3,2,'Możesz mnie zasadzić!','2020-03-15 00:00:00'),(64,4,2,'Nie zapomnij mnie zasadzić!','2020-05-30 00:00:00'),(65,4,2,'Możesz mnie zasadzić!','2020-03-01 00:00:00'),(70,8,2,'Nie zapomnij mnie zasadzić!','2020-05-30 00:00:00'),(71,8,2,'Możesz mnie zasadzić!','2020-03-01 00:00:00'),(72,10,1,'Nie zapomnij mnie zasadzić!','2020-04-15 00:00:00'),(73,10,1,'Możesz mnie zasadzić!','2020-03-15 00:00:00'),(74,15,1,'Nie zapomnij mnie zasadzić!','2020-04-15 00:00:00'),(75,15,1,'Możesz mnie zasadzić!','2020-03-15 00:00:00'),(76,17,1,'Nie zapomnij mnie zasadzić!','2020-04-15 00:00:00'),(77,17,1,'Możesz mnie zasadzić!','2020-03-15 00:00:00'),(78,18,1,'Nie zapomnij mnie zasadzić!','2020-04-15 00:00:00'),(79,18,1,'Możesz mnie zasadzić!','2020-03-15 00:00:00');
/*!40000 ALTER TABLE `powiadomienia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rosliny`
--

DROP TABLE IF EXISTS `rosliny`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rosliny` (
  `id_r` int NOT NULL AUTO_INCREMENT,
  `nazwa` varchar(100) NOT NULL,
  `odmiana` tinytext NOT NULL,
  `okres_siewu_poczatek` datetime NOT NULL,
  `okres_siewu_koniec` datetime NOT NULL,
  `okres_zbioru_poczatek` datetime NOT NULL,
  `okres_zbioru_koniec` datetime NOT NULL,
  `czestotliwosc_podlewania` int NOT NULL,
  PRIMARY KEY (`id_r`),
  UNIQUE KEY `id_r` (`id_r`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rosliny`
--

LOCK TABLES `rosliny` WRITE;
/*!40000 ALTER TABLE `rosliny` DISABLE KEYS */;
INSERT INTO `rosliny` VALUES (1,'Marchew','wczesna','2020-03-15 00:00:00','2020-04-15 00:00:00','2020-06-01 00:00:00','2020-07-30 00:00:00',3),(2,'Pietruszka','naciowa Mooskrause 2 - kędzieżawa','2020-03-01 00:00:00','2020-05-30 00:00:00','2020-05-30 00:00:00','2020-12-31 00:00:00',7),(3,'Burak','Cukrowy podłużny Opolski','2020-03-15 00:00:00','2020-04-15 00:00:00','2020-07-15 00:00:00','2020-09-30 00:00:00',14),(5,'Koper','ogrodowy Sprinter','2020-03-01 00:00:00','2020-04-30 00:00:00','2020-05-01 00:00:00','2020-10-30 00:00:00',1),(6,'Dynia','olbrzymia Uchiki Kuri','2020-05-01 00:00:00','2020-05-30 00:00:00','2020-08-01 00:00:00','2020-09-30 00:00:00',2);
/*!40000 ALTER TABLE `rosliny` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `uzytkownicy`
--

DROP TABLE IF EXISTS `uzytkownicy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `uzytkownicy` (
  `id_u` int NOT NULL AUTO_INCREMENT,
  `nazwa` varchar(30) NOT NULL,
  `login` varchar(30) NOT NULL,
  `haslo` tinytext NOT NULL,
  PRIMARY KEY (`id_u`),
  UNIQUE KEY `id_u` (`id_u`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uzytkownicy`
--

LOCK TABLES `uzytkownicy` WRITE;
/*!40000 ALTER TABLE `uzytkownicy` DISABLE KEYS */;
INSERT INTO `uzytkownicy` VALUES (1,'Magdalena','Madzia','haslo'),(2,'Filip','FFFFX','haslo'),(3,'Noname','tester','haslotestera'),(5,'Noname','tester2','haslotestera2'),(6,'Tester3','LoginTestera','hasloDlaInnegoTestera');
/*!40000 ALTER TABLE `uzytkownicy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `zasady`
--

DROP TABLE IF EXISTS `zasady`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `zasady` (
  `id_z` int NOT NULL AUTO_INCREMENT,
  `id_rosliny` int NOT NULL,
  `tresc` varchar(1000) NOT NULL,
  PRIMARY KEY (`id_z`),
  UNIQUE KEY `id_z` (`id_z`),
  KEY `id_rosliny` (`id_rosliny`),
  CONSTRAINT `zasady_ibfk_1` FOREIGN KEY (`id_rosliny`) REFERENCES `rosliny` (`id_r`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zasady`
--

LOCK TABLES `zasady` WRITE;
/*!40000 ALTER TABLE `zasady` DISABLE KEYS */;
INSERT INTO `zasady` VALUES (1,5,'Stanowisko: słoneczne‚cieniste, z podłożem przepuszczalnym, próchnicznym i nieprzesychającym'),(2,5,'gatunek nie jest bowiem odporny na spadki temperatury poniżej 0°C'),(3,3,'Stanowisko: Burak lubi rosnąć w miejscu z dostępem do światła, chociaż stanowisko pod uprawę tych warzyw nie powinno znajdować się w pełnym słońcu. Jest to roślina, która najlepiej czuje się na glebie wilgotnej i lekkiej. Optymalny dla jego rozwoju jest obojętny odczyn pH podłoża. Do uprawy buraka Opolskiego w miarę możliwości wybieramy mniej kamieniste miejsca.'),(4,3,'Rozstawa: Nasiona buraka Opolskiego wysiewamy w rzędach oddalonych o 30-40 centymetrów. Po wzejściu roślin robimy przerywkę pozostawiając rośliny co około 5 centymetrów.'),(5,2,'Warunki uprawy: pietruszkę naciową należy uprawiać na glebach lekkich, obojętnych i bogatych w próchnicę. Należy unikać gleb podmokłych, kamienistych i zaskorupiających się.');
/*!40000 ALTER TABLE `zasady` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-05-05 13:32:04
