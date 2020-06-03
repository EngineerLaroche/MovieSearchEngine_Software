-- Script lu par l'app java

-- Drop Contraintes
ALTER TABLE GENRES          DROP CONSTRAINT FKGENRES;
ALTER TABLE GENRES          DROP CONSTRAINT FKIDFILMS;
ALTER TABLE PAYS            DROP CONSTRAINT FKPAYS;
ALTER TABLE PAYS            DROP CONSTRAINT FKIDFILMS_PAYS;
ALTER TABLE REALISATEURS    DROP CONSTRAINT FKFILMS_REALISATEUR ;   
ALTER TABLE SCENARISTES     DROP CONSTRAINT FKFILMS_SCENARISTE;
ALTER TABLE ROLES           DROP CONSTRAINT FKFILMS_ROLES;
ALTER TABLE ANNONCES        DROP CONSTRAINT FKIDFILMS_ANNONCES;
ALTER TABLE GENRES_UNIQUE   DROP CONSTRAINT PKGENRE;
ALTER TABLE PAYS_UNIQUE     DROP CONSTRAINT PKPAYS;

ALTER TABLE FILMS           DROP CONSTRAINT PKFILMS;

-- Drop Tables
DROP TABLE FILMS; 
DROP TABLE GENRES;
DROP TABLE PAYS;
DROP TABLE GENRES_UNIQUE;
DROP TABLE PAYS_UNIQUE;
DROP TABLE REALISATEURS;
DROP TABLE SCENARISTES;
DROP TABLE ROLES;
DROP TABLE ANNONCES;

-- Create Tables
CREATE TABLE FILMS (
  IDFILM      	INTEGER,
  TITRE       	VARCHAR(90),
  ANNEE       	VARCHAR(50),
  LANGUE      	VARCHAR(50),
  DUREE       	VARCHAR(50),
  RESUME      	VARCHAR(500),
  POSTER      	VARCHAR(120)
);

CREATE TABLE GENRES_UNIQUE (
  GENRE       VARCHAR(90)
);

CREATE TABLE PAYS_UNIQUE (
  PAYS        VARCHAR(90)
);

CREATE TABLE GENRES (
  GENRE    		VARCHAR(90),
  IDFILM   		INTEGER
);

CREATE TABLE PAYS (
  PAYS    		VARCHAR(90),
  IDFILM  		INTEGER
);

CREATE TABLE REALISATEURS (
  IDPERSONNE     INTEGER,
  IDFILM         INTEGER
);

CREATE TABLE SCENARISTES (
  IDPERSONNE     INTEGER,
  IDFILM         INTEGER
);

CREATE TABLE ROLES (
  IDPERSONNE    INTEGER,
  PERSONNAGE    VARCHAR(90),
  IDFILM        INTEGER 
);

CREATE TABLE ANNONCES (
  LIEN      	VARCHAR(500),  
  IDFILM    	INTEGER
);

-- Ajout Contraintes
 ALTER TABLE FILMS ADD CONSTRAINT PKFILMS PRIMARY KEY (IDFILM);
 ALTER TABLE GENRES_UNIQUE ADD CONSTRAINT PKGENRE PRIMARY KEY (GENRE);
 ALTER TABLE PAYS_UNIQUE ADD CONSTRAINT PKPAYS PRIMARY KEY (PAYS);
 ALTER TABLE GENRES ADD CONSTRAINT FKGENRES  FOREIGN KEY (GENRE) REFERENCES GENRES_UNIQUE (GENRE);
 ALTER TABLE GENRES ADD CONSTRAINT FKIDFILMS FOREIGN KEY (IDFILM) REFERENCES FILMS (IDFILM);
 ALTER TABLE PAYS ADD CONSTRAINT FKPAYS   FOREIGN KEY(PAYS) REFERENCES PAYS_UNIQUE (PAYS);
 ALTER TABLE PAYS ADD CONSTRAINT FKIDFILMS_PAYS FOREIGN KEY (IDFILM) REFERENCES FILMS (IDFILM);
 ALTER TABLE REALISATEURS ADD CONSTRAINT FKFILMS_REALISATEUR FOREIGN KEY (IDFILM) REFERENCES FILMS (IDFILM);   
 ALTER TABLE SCENARISTES ADD CONSTRAINT FKFILMS_SCENARISTE   FOREIGN KEY (IDFILM) REFERENCES FILMS (IDFILM);
 ALTER TABLE ROLES ADD CONSTRAINT FKFILMS_ROLES FOREIGN KEY (IDFILM) REFERENCES FILMS (IDFILM);
 ALTER TABLE ANNONCES ADD CONSTRAINT FKIDFILMS_ANNONCES FOREIGN KEY (IDFILM) REFERENCES FILMS (IDFILM);

COMMIT;