-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         10.4.32-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versión:             12.11.0.7065
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para optiplan_db
CREATE DATABASE IF NOT EXISTS `optiplan_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `optiplan_db`;

-- Volcando estructura para tabla optiplan_db.categorias
CREATE TABLE IF NOT EXISTS `categorias` (
  `categoriaid` bigint(20) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`categoriaid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla optiplan_db.categorias: ~1 rows (aproximadamente)
INSERT INTO `categorias` (`categoriaid`, `nombre`) VALUES
	(1, 'Estudio');

-- Volcando estructura para tabla optiplan_db.tareas
CREATE TABLE IF NOT EXISTS `tareas` (
  `tareaid` bigint(20) NOT NULL AUTO_INCREMENT,
  `descripcion` text DEFAULT NULL,
  `estado` varchar(20) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `fecha_creacion` datetime(6) DEFAULT NULL,
  `hora_fin` time DEFAULT NULL,
  `hora_inicio` time DEFAULT NULL,
  `hora_recordatorio` time DEFAULT NULL,
  `prioridad` varchar(20) DEFAULT NULL,
  `recordatorio_activo` bit(1) DEFAULT NULL,
  `titulo` varchar(100) DEFAULT NULL,
  `categoriaid` bigint(20) DEFAULT NULL,
  `usuarioid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`tareaid`),
  KEY `FKkgedlhjot8k23dm01n25mfehy` (`categoriaid`),
  KEY `FKmhy8ho9ybcv94fx6sc9j8bni5` (`usuarioid`),
  CONSTRAINT `FKkgedlhjot8k23dm01n25mfehy` FOREIGN KEY (`categoriaid`) REFERENCES `categorias` (`categoriaid`),
  CONSTRAINT `FKmhy8ho9ybcv94fx6sc9j8bni5` FOREIGN KEY (`usuarioid`) REFERENCES `usuarios` (`usuarioid`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla optiplan_db.tareas: ~7 rows (aproximadamente)
INSERT INTO `tareas` (`tareaid`, `descripcion`, `estado`, `fecha`, `fecha_creacion`, `hora_fin`, `hora_inicio`, `hora_recordatorio`, `prioridad`, `recordatorio_activo`, `titulo`, `categoriaid`, `usuarioid`) VALUES
	(1, 'afaw', 'Completado', '2025-12-01', '2025-11-24 00:08:36.000000', '21:10:00', NULL, '19:22:00', 'Media', b'1', 'tarea', 1, 1),
	(2, 'waraw', 'Completado', '2025-11-23', '2025-11-24 00:20:54.000000', '19:22:00', NULL, '19:22:00', 'Alta', b'1', 'tarea2', 1, 1),
	(4, 'wdaw', 'Completado', '2025-11-21', '2025-11-24 00:23:18.000000', '19:24:00', NULL, NULL, 'Alta', b'0', 'tarea2', 1, 1),
	(5, 'afw', 'Completado', '2025-11-23', '2025-11-24 00:27:52.000000', '19:29:00', NULL, NULL, 'Media', b'0', 'tarea3', 1, 1),
	(6, '134', 'Pendiente', '2025-11-23', '2025-11-24 00:34:17.000000', '19:36:00', NULL, '19:36:00', 'Alta', b'1', 'tarea134', 1, 1),
	(7, 'etata', 'Pendiente', '2025-11-28', '2025-11-24 00:36:43.000000', '19:39:00', NULL, '19:40:00', 'Alta', b'1', 'aet', 1, 1),
	(8, 'aet', 'Pendiente', '2025-11-25', '2025-11-24 00:37:15.000000', '19:40:00', NULL, '07:39:00', 'Media', b'1', 'ateatae', 1, 1);

-- Volcando estructura para tabla optiplan_db.usuarios
CREATE TABLE IF NOT EXISTS `usuarios` (
  `usuarioid` bigint(20) NOT NULL AUTO_INCREMENT,
  `contrasena` varchar(256) DEFAULT NULL,
  `correo` varchar(100) DEFAULT NULL,
  `fecha_registro` datetime(6) DEFAULT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`usuarioid`),
  UNIQUE KEY `UKcdmw5hxlfj78uf4997i3qyyw5` (`correo`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla optiplan_db.usuarios: ~1 rows (aproximadamente)
INSERT INTO `usuarios` (`usuarioid`, `contrasena`, `correo`, `fecha_registro`, `nombre`) VALUES
	(1, '$2a$10$OzN82GDkJ0x3lt6I6xoFUe4p/UWkRExfpXjmQ23hAKDJsDg9F8WXG', 'correo@gmail.com', '2025-11-24 00:03:46.000000', 'Fabricio');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
