DROP DATABASE hospedix;

CREATE DATABASE IF NOT EXISTS hospedix;

USE hospedix;

CREATE TABLE empleados (
    dni VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(50),
    apellido VARCHAR(50),
    telefono VARCHAR(20),
    direccion VARCHAR(200),
    cargo ENUM('Recepcionista', 'Gerente', 'Limpieza', 'Mantenimiento', 'Hostelería', 'Otros'),
    horario_trabajo VARCHAR(100)
);

CREATE TABLE habitaciones (
    num_habitacion INT PRIMARY KEY,
    estado ENUM('Disponible', 'Ocupada', 'Mantenimiento'),
    tipo ENUM('Individual', 'Doble', 'Suite'),
    precio DECIMAL(10,2)
);

CREATE TABLE huesped (
    dni VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(50),
    apellido VARCHAR(50),
    telefono VARCHAR(20)
);

CREATE TABLE reservas (
    id_reserva INT AUTO_INCREMENT PRIMARY KEY,
    num_habitacion INT,
    dni_huesped VARCHAR(20),
    fecha_checkin DATE,
    fecha_checkout DATE,
    estado_pago ENUM('Pagado', 'Pendiente', 'Cancelado'),
    cantidad_personas INT,
    precio INT,
    extras INT,
    FOREIGN KEY (num_habitacion) REFERENCES habitaciones(num_habitacion),
    FOREIGN KEY (dni_huesped) REFERENCES huesped(dni)
);

CREATE TABLE incidencias (
    id_incidencia INT AUTO_INCREMENT PRIMARY KEY,
    tipo_incidencia ENUM('Limpieza', 'Mantenimiento', 'Otros'),
    descripcion VARCHAR(100),
    fecha_reporte DATE,
    num_habitacion INT,
    estado ENUM('Abierto', 'Cerrado'),
    FOREIGN KEY (num_habitacion) REFERENCES habitaciones(num_habitacion)
);