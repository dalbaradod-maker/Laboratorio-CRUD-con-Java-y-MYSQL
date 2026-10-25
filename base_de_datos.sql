-- Base de datos del caso Transporte.
-- No borra ninguna tabla ni los registros que ya tengas.
CREATE DATABASE IF NOT EXISTS db_paqueteria CHARACTER SET utf8mb4;
USE db_paqueteria;

CREATE TABLE IF NOT EXISTS camion (
    idCamion INT AUTO_INCREMENT PRIMARY KEY,
    NoPlaca VARCHAR(15) NOT NULL UNIQUE,
    Modelo VARCHAR(60) NOT NULL,
    Tipo VARCHAR(40) NOT NULL,
    Potencia INT NOT NULL
);

CREATE TABLE IF NOT EXISTS camionero (
    idCamionero INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(80) NOT NULL,
    Telefono VARCHAR(8) NOT NULL,
    Direccion VARCHAR(120) NOT NULL,
    Salario DECIMAL(10,2) NOT NULL,
    Poblacion VARCHAR(80) NOT NULL
);

-- Ciudad es necesaria para elegir un destino en la tabla paquete.
CREATE TABLE IF NOT EXISTS ciudad (
    idCiudad INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(80) NOT NULL
);

CREATE TABLE IF NOT EXISTS paquete (
    idPaquete INT AUTO_INCREMENT PRIMARY KEY,
    Descripcion VARCHAR(150) NOT NULL,
    Destinatario VARCHAR(80) NOT NULL,
    DireccionEnvio VARCHAR(150) NOT NULL,
    idCamionero INT NOT NULL,
    idCiudad INT NOT NULL,
    FOREIGN KEY (idCamionero) REFERENCES camionero(idCamionero),
    FOREIGN KEY (idCiudad) REFERENCES ciudad(idCiudad)
);

CREATE TABLE IF NOT EXISTS camionerocamion (
    idCamioneroCamion INT AUTO_INCREMENT PRIMARY KEY,
    idCamionero INT NOT NULL,
    idCamion INT NOT NULL,
    UNIQUE KEY pareja_unica (idCamionero, idCamion),
    FOREIGN KEY (idCamionero) REFERENCES camionero(idCamionero),
    FOREIGN KEY (idCamion) REFERENCES camion(idCamion)
);

-- Ciudades de ejemplo. Se insertan solo si aun no existen por nombre.
INSERT INTO ciudad (Nombre)
SELECT 'San Marcos' WHERE NOT EXISTS (SELECT 1 FROM ciudad WHERE Nombre = 'San Marcos');
INSERT INTO ciudad (Nombre)
SELECT 'Quetzaltenango' WHERE NOT EXISTS (SELECT 1 FROM ciudad WHERE Nombre = 'Quetzaltenango');
INSERT INTO ciudad (Nombre)
SELECT 'Guatemala' WHERE NOT EXISTS (SELECT 1 FROM ciudad WHERE Nombre = 'Guatemala');

SHOW TABLES;
SELECT * FROM ciudad;
