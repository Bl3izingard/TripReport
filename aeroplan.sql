-- phpMyAdmin SQL Dump
-- version 4.6.6deb4
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Oct 04, 2018 at 11:07 AM
-- Server version: 10.1.26-MariaDB-0+deb9u1
-- PHP Version: 7.0.19-1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `aeroplan`
--
DROP DATABASE IF EXISTS `aeroplan`;
CREATE DATABASE IF NOT EXISTS `aeroplan` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `aeroplan`;

-- --------------------------------------------------------

--
-- Table structure for table `Aeroport`
--

CREATE TABLE `Aeroport` (
  `id` int(11) NOT NULL,
  `oaci` varchar(10) NOT NULL,
  `aita` varchar(10) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `latitude` float NOT NULL,
  `longitude` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Aeroport`
--

INSERT INTO `Aeroport` (`id`, `oaci`, `aita`, `nom`, `latitude`, `longitude`) VALUES
(2, 'KJFK', 'JFK', 'New York Jfk (Aéroport international John-F.-Kennedy) - Etats Unis', 40.6444, -73.778),
(1, 'LFPG', 'CDG', 'Paris Charles De Gaulle (Aéroport Paris-Charles-de-Gaulle) - France', 49.0029, 2.55329);

-- --------------------------------------------------------

--
-- Table structure for table `Avion`
--

CREATE TABLE `Avion` (
  `id` int(11) NOT NULL,
  `modele` varchar(125) NOT NULL,
  `numeroSerie` varchar(125) NOT NULL,
  `codeInterne` varchar(125) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Avion`
--

INSERT INTO `Avion` (`id`, `modele`, `numeroSerie`, `codeInterne`) VALUES
(1, 'Airbus A300', 'A300-123AZE123', 'A300');

-- --------------------------------------------------------

--
-- Table structure for table `Mouvement`
--

CREATE TABLE `Mouvement` (
  `id` int(11) NOT NULL,
  `numeroVol` varchar(30) NOT NULL,
  `distance` int(11) NOT NULL,
  `nbPassagers` int(11) NOT NULL,
  `estIntracom` tinyint(1) NOT NULL,
  `dateHeureDepart` datetime NOT NULL,
  `dateHeureArrivee` datetime NOT NULL,
  `dureeVol` int(11) NOT NULL,
  `AeroportDepart_oaci` varchar(10) NOT NULL,
  `AeroportArrivee_oaci` varchar(10) NOT NULL,
  `Avion_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Mouvement`
--

INSERT INTO `Mouvement` (`id`, `numeroVol`, `distance`, `nbPassagers`, `estIntracom`, `dateHeureDepart`, `dateHeureArrivee`, `dureeVol`, `AeroportDepart_oaci`, `AeroportArrivee_oaci`, `Avion_id`) VALUES
(1, 'A300-1', 2000, 100, 1, '2017-09-30 09:00:00', '2017-09-30 19:00:00', 10, 'KJFK', 'LFPG', 1);

-- --------------------------------------------------------

--
-- Table structure for table `Retard`
--

CREATE TABLE `Retard` (
  `id` int(11) NOT NULL,
  `commentaire` varchar(255) NOT NULL,
  `duree` int(11) NOT NULL,
  `Mouvement_id` int(11) NOT NULL,
  `TypeRetard_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `TypeRetard`
--

CREATE TABLE `TypeRetard` (
  `id` int(11) NOT NULL,
  `codeSituation` varchar(2) NOT NULL,
  `nom` varchar(125) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `TypeRetard`
--

INSERT INTO `TypeRetard` (`id`, `codeSituation`, `nom`) VALUES
(1, 'AV', 'Occupation des pistes'),
(2, 'EV', 'Situation Climatique');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Aeroport`
--
ALTER TABLE `Aeroport`
  ADD PRIMARY KEY (`oaci`),
  ADD UNIQUE KEY `id` (`id`);

--
-- Indexes for table `Avion`
--
ALTER TABLE `Avion`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `Mouvement`
--
ALTER TABLE `Mouvement`
  ADD PRIMARY KEY (`id`),
  ADD KEY `AeroportDepart_oaci` (`AeroportDepart_oaci`),
  ADD KEY `AeroportArrivee_oaci` (`AeroportArrivee_oaci`),
  ADD KEY `Avion_id` (`Avion_id`);

--
-- Indexes for table `Retard`
--
ALTER TABLE `Retard`
  ADD PRIMARY KEY (`id`),
  ADD KEY `TypeRetard_id` (`TypeRetard_id`),
  ADD KEY `Mouvement_id` (`Mouvement_id`);

--
-- Indexes for table `TypeRetard`
--
ALTER TABLE `TypeRetard`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Aeroport`
--
ALTER TABLE `Aeroport`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `Avion`
--
ALTER TABLE `Avion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `Mouvement`
--
ALTER TABLE `Mouvement`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `Retard`
--
ALTER TABLE `Retard`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `TypeRetard`
--
ALTER TABLE `TypeRetard`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `Mouvement`
--
ALTER TABLE `Mouvement`
  ADD CONSTRAINT `Mouvement_ibfk_1` FOREIGN KEY (`AeroportDepart_oaci`) REFERENCES `Aeroport` (`oaci`),
  ADD CONSTRAINT `Mouvement_ibfk_2` FOREIGN KEY (`AeroportArrivee_oaci`) REFERENCES `Aeroport` (`oaci`),
  ADD CONSTRAINT `Mouvement_ibfk_3` FOREIGN KEY (`Avion_id`) REFERENCES `Avion` (`id`);

--
-- Constraints for table `Retard`
--
ALTER TABLE `Retard`
  ADD CONSTRAINT `Retard_ibfk_1` FOREIGN KEY (`TypeRetard_id`) REFERENCES `TypeRetard` (`id`),
  ADD CONSTRAINT `Retard_ibfk_2` FOREIGN KEY (`Mouvement_id`) REFERENCES `Mouvement` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
