-- SQL Aeroplan
-- version 2
-- Generation Time: Jan 30, 2018 at 12:56 PM

--
-- Table structure for table `Aeroport`
--

CREATE TABLE `Aeroport`(
  `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  `oaci` varchar(10) NOT NULL UNIQUE,
  `aita` varchar(10) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `latitude` float NOT NULL,
  `longitude` float NOT NULL);

--
-- Dumping data for table `Aeroport`
--

INSERT INTO `Aeroport` (`oaci`, `aita`, `nom`, `latitude`, `longitude`) VALUES
('KJFK', 'JFK', 'New York Jfk (Aéroport international John-F.-Kennedy) - Etats Unis', 40.6444, -73.778),
('LFPG', 'CDG', 'Paris Charles De Gaulle (Aéroport Paris-Charles-de-Gaulle) - France', 49.0029, 2.55329);

-- --------------------------------------------------------

--
-- Table structure for table `Avion`
--

CREATE TABLE `Avion`(
  `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  `modele` varchar(125) NOT NULL,
  `numeroSerie` varchar(125) NOT NULL,
  `codeInterne` varchar(125) NOT NULL);

--
-- Dumping data for table `Avion`
--

INSERT INTO `Avion` (`modele`, `numeroSerie`, `codeInterne`) VALUES
('Airbus A300', 'A300-123AZE123', 'A300');

-- --------------------------------------------------------

--
-- Table structure for table `Mouvement`
--

CREATE TABLE `Mouvement` (
  `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  `numeroVol` varchar(30) NOT NULL,
  `distance` INTEGER NOT NULL,
  `nbPassagers` INTEGER NOT NULL,
  `estIntracom` tinyint(1) NOT NULL,
  `dateHeureDepart` datetime NOT NULL,
  `dateHeureArrivee` datetime NOT NULL,
  `dureeVol` INTEGER NOT NULL,
  `AeroportDepart_oaci` varchar(10) NOT NULL,
  `AeroportArrivee_oaci` varchar(10) NOT NULL,
  `Avion_id` INTEGER NOT NULL,
  FOREIGN KEY (`AeroportDepart_oaci`) REFERENCES `Aeroport` (`oaci`),
  FOREIGN KEY (`AeroportArrivee_oaci`) REFERENCES `Aeroport` (`oaci`),
  FOREIGN KEY (`Avion_id`) REFERENCES `Avion` (`id`)
);

--
-- Dumping data for table `Mouvement`
--

INSERT INTO `Mouvement` (`numeroVol`, `distance`, `nbPassagers`, `estIntracom`, `dateHeureDepart`, `dateHeureArrivee`, `dureeVol`, `AeroportDepart_oaci`, `AeroportArrivee_oaci`, `Avion_id`) VALUES
('A300-1', 2000, 100, 1, '2017-09-30 09:00:00', '2017-09-30 19:00:00', 10, 'KJFK', 'LFPG', 1);

-- --------------------------------------------------------

--
-- Table structure for table `Retard`
--

CREATE TABLE `Retard` (
  `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  `commentaire` varchar(255) NOT NULL,
  `duree` INTEGER NOT NULL,
  `impliqueAeroport` bit(1) NOT NULL,
  `Mouvement_id` INTEGER NOT NULL,
  `TypeRetard_id` INTEGER NOT NULL,
   FOREIGN KEY (`TypeRetard_id`) REFERENCES `TypeRetard` (`id`),
   FOREIGN KEY (`Mouvement_id`) REFERENCES `Mouvement` (`id`)
);

-- --------------------------------------------------------

--
-- Table structure for table `TypeRetard`
--

CREATE TABLE `TypeRetard` (
  `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  `codeSituation` varchar(2) NOT NULL,
  `nom` varchar(125) NOT NULL
);

--
-- Dumping data for table `TypeRetard`
--

INSERT INTO `TypeRetard` (`codeSituation`, `nom`) VALUES
('AV', 'Occupation des pistes'),
('EV', 'Situation Climatique');
