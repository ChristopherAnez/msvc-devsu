CREATE DATABASE account_management_db;
CREATE DATABASE user_management_db;

CREATE TABLE Persona (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    genero VARCHAR(100) NOT NULL,
    edad INT NOT NULL,
    identificacion VARCHAR(20) UNIQUE NOT NULL,
    direccion VARCHAR(200),
    telefono VARCHAR(20)
);

CREATE TABLE Cliente (
    cliente_id SERIAL PRIMARY KEY,
    persona_id INT NOT NULL,
    contrasena VARCHAR(100) NOT NULL,
    estado BOOLEAN NOT NULL
);


CREATE TABLE Cuenta (
    id_cuenta SERIAL PRIMARY KEY,
    id_cliente INT NOT NULL,
    numerocuenta VARCHAR(20) NOT NULL UNIQUE,
    tipo VARCHAR(50) NOT NULL,
    saldoinicial NUMERIC(15, 2) NOT NULL,
	saldodisponible NUMERIC(15, 2) NOT NULL,
    estado BOOLEAN NOT NULL
);

CREATE TABLE Movimientos (
    id SERIAL PRIMARY KEY,
    fecha DATE NOT NULL,
    tipomovimiento VARCHAR(50) NOT NULL,
    valor NUMERIC(15, 2) NOT NULL,
    saldo NUMERIC(15, 2) NOT NULL,
    numerocuenta VARCHAR(20) NOT NULL,
    FOREIGN KEY (numerocuenta) REFERENCES Cuenta(numerocuenta)
);