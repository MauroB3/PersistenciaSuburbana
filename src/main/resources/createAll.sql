DROP SCHEMA IF EXISTS bichomon;
CREATE SCHEMA bichomon;

USE bichomon;

CREATE TABLE especie (
  id int NOT NULL,
  nombre VARCHAR(191) NOT NULL UNIQUE,
  peso int NOT NULL,
  altura int NOT NULL,
  tipo VARCHAR(50) NOT NULL,
  cantidad int NOT NULL,
  PRIMARY KEY (nombre)
)
ENGINE = InnoDB;
