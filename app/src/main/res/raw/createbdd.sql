CREATE TABLE IF NOT EXISTS Aeroport (id INTEGER NOT NULL PRIMARY KEY ASC, oaci TEXT NOT NULL UNIQUE, aita TEXT NOT NULL, nom TEXT NOT NULL, latitude REAL NOT NULL, longitude REAL NOT NULL);


INSERT INTO Aeroport (oaci, aita, nom, latitude, longitude) VALUES
('KJFK', 'JFK', 'New York Jfk (Aéroport international John-F.-Kennedy) - Etats Unis', 40.6444, -73.778),
('LFPG', 'CDG', 'Paris Charles De Gaulle (Aéroport Paris-Charles-de-Gaulle) - France', 49.0029, 2.55329);


CREATE TABLE IF NOT EXISTS Avion(
  id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  modele TEXT NOT NULL,
  numeroSerie TEXT NOT NULL,
  codeInterne TEXT NOT NULL);


INSERT INTO Avion (modele, numeroSerie, codeInterne) VALUES
('Airbus A300', 'A300-123AZE123', 'A300');

CREATE TABLE IF NOT EXISTS Mouvement (
  id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  numeroVol varchar(30) NOT NULL,
  distance INTEGER NOT NULL,
  nbPassagers INTEGER NOT NULL,
  estIntracom INTEGER NOT NULL,
  dateHeureDepart TEXT NOT NULL,
  dateHeureArrivee TEXT NOT NULL,
  dureeVol INTEGER NOT NULL,
  AeroportDepart_oaci TEXT NOT NULL,
  AeroportArrivee_oaci TEXT NOT NULL,
  Avion_id INTEGER NOT NULL,
  FOREIGN KEY (AeroportDepart_oaci) REFERENCES Aeroport (oaci),
  FOREIGN KEY (AeroportArrivee_oaci) REFERENCES Aeroport (oaci),
  FOREIGN KEY (Avion_id) REFERENCES Avion (id)
);

INSERT INTO Mouvement (numeroVol, distance, nbPassagers, estIntracom, dateHeureDepart, dateHeureArrivee, dureeVol, AeroportDepart_oaci, AeroportArrivee_oaci, Avion_id) VALUES
('A300-1', 2000, 100, 1, '2017-09-30 09:00:00', '2017-09-30 19:00:00', 10, 'KJFK', 'LFPG', 1);


CREATE TABLE IF NOT EXISTS Retard (
  id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  commentaire TEXT NOT NULL,
  duree INTEGER NOT NULL,
  impliqueAeroport INTEGER NOT NULL,
  Mouvement_id INTEGER NOT NULL,
  TypeRetard_id INTEGER NOT NULL,
   FOREIGN KEY (TypeRetard_id) REFERENCES TypeRetard (id),
   FOREIGN KEY (Mouvement_id) REFERENCES Mouvement (id)
);

CREATE TABLE IF NOT EXISTS TypeRetard (
  id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  codeSituation TEXT NOT NULL,
  nom TEXT NOT NULL
);


INSERT INTO TypeRetard (codeSituation, nom) VALUES
('AV', 'Occupation des pistes'),
('EV', 'Situation Climatique');
