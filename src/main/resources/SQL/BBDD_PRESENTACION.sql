DROP DATABASE IF EXISTS hospedix;

CREATE DATABASE IF NOT EXISTS hospedix;

USE hospedix;

-- Tabla de empleados
CREATE TABLE empleados (
    dni VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(50),
    apellido VARCHAR(50),
    telefono VARCHAR(20),
    direccion VARCHAR(200),
    cargo ENUM('Recepcionista', 'Gerente', 'Limpieza', 'Mantenimiento', 'Hostelería', 'Otros'),
    horario_trabajo VARCHAR(100)
);

-- Tabla de habitaciones
CREATE TABLE habitaciones (
    num_habitacion INT PRIMARY KEY,
    estado ENUM('Disponible', 'Ocupada', 'Mantenimiento'),
    tipo ENUM('Individual', 'Doble', 'Suite'),
    precio DECIMAL(10,2)
);

-- Tabla de huéspedes
CREATE TABLE huesped (
    dni VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(50),
    apellido VARCHAR(50),
    telefono VARCHAR(20)
);

-- Tabla de reservas
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

-- Tabla de incidencias
CREATE TABLE incidencias (
    id_incidencia INT AUTO_INCREMENT PRIMARY KEY,
    tipo_incidencia ENUM('Limpieza', 'Mantenimiento', 'Otros'),
    descripcion VARCHAR(100),
    fecha_reporte DATE,
    num_habitacion INT,
    estado ENUM('Abierto', 'Cerrado'),
    FOREIGN KEY (num_habitacion) REFERENCES habitaciones(num_habitacion)
);

-- Empleados
INSERT INTO empleados (dni, nombre, apellido, telefono, direccion, cargo, horario_trabajo) VALUES
('11111111A', 'admin', 'admin', '000000000', 'Dirección genérica', 'Gerente', 'Lunes a Viernes, 9:00 - 17:00'),
('22222222B', 'Laura', 'Martínez', '600123456', 'Calle Luna 12', 'Recepcionista', 'Lunes a Viernes, 8:00 - 16:00'),
('33333333C', 'Carlos', 'Gómez', '600234567', 'Av. Sol 45', 'Limpieza', 'Martes a Sábado, 7:00 - 15:00'),
('44444444D', 'Sofía', 'López', '600345678', 'Calle Estrella 9', 'Mantenimiento', 'Lunes a Viernes, 9:00 - 17:00'),
('55555555E', 'Javier', 'Torres', '644444444', 'Calle Norte 18', 'Hostelería', 'Miércoles a Domingo, 10:00 - 18:00'),
('66666666F', 'Elena', 'Muñoz', '655555555', 'Paseo del Río 3', 'Recepcionista', 'Lunes a Viernes, 14:00 - 22:00'),
('77777777G', 'Diego', 'Navarro', '666666666', 'Camino Real 77', 'Otros', 'Sábados y Domingos, 8:00 - 20:00');

-- Habitaciones
INSERT INTO habitaciones (num_habitacion, estado, tipo, precio) VALUES
(101, 'Disponible', 'Individual', 50.00),
(102, 'Ocupada', 'Doble', 80.00),
(103, 'Disponible', 'Suite', 150.00),
(104, 'Mantenimiento', 'Doble', 75.00),
(105, 'Disponible', 'Individual', 55.00),
(106, 'Ocupada', 'Suite', 160.00),
(107, 'Disponible', 'Doble', 85.00),
(108, 'Mantenimiento', 'Suite', 170.00),
(109, 'Disponible', 'Individual', 45.00),
(110, 'Ocupada', 'Doble', 90.00);

-- Huéspedes
INSERT INTO huesped (dni, nombre, apellido, telefono) VALUES
('88888888H', 'Ana', 'Pérez', '611111111'),
('99999999I', 'Luis', 'Ramírez', '622222222'),
('12345678J', 'Marta', 'Ruiz', '633333333'),
('23456789K', 'Pedro', 'Sánchez', '644444444'),
('34567890L', 'Lucía', 'García', '655555555'),
('45678901M', 'Marcos', 'Díaz', '666666666'),
('56789012N', 'Clara', 'Serrano', '677777777'),
('67890123O', 'Tomás', 'Castro', '688888888');

-- Reservas
INSERT INTO reservas (num_habitacion, dni_huesped, fecha_checkin, fecha_checkout, estado_pago, cantidad_personas, precio, extras) VALUES
(102, '88888888H', '2025-05-20', '2025-05-25', 'Pagado', 2, 400, 50),
(103, '99999999I', '2025-06-01', '2025-06-03', 'Pendiente', 1, 300, 0),
(105, '23456789K', '2025-05-15', '2025-05-18', 'Pagado', 1, 165, 0),
(106, '34567890L', '2025-05-22', '2025-05-27', 'Pendiente', 2, 800, 100),
(107, '45678901M', '2025-06-10', '2025-06-12', 'Cancelado', 2, 170, 0),
(109, '56789012N', '2025-06-05', '2025-06-09', 'Pagado', 1, 180, 0),
(110, '67890123O', '2025-05-20', '2025-05-23', 'Pagado', 2, 270, 30),
(101, '88888888H', '2025-06-15', '2025-06-20', 'Pendiente', 1, 250, 20);

-- Incidencias
INSERT INTO incidencias (tipo_incidencia, descripcion, fecha_reporte, num_habitacion, estado) VALUES
('Limpieza', 'Sábanas manchadas', '2025-05-21', 104, 'Abierto'),
('Mantenimiento', 'Aire acondicionado no funciona', '2025-05-20', 102, 'Cerrado'),
('Mantenimiento', 'Problemas con la cerradura', '2025-05-19', 105, 'Cerrado'),
('Limpieza', 'Mal olor en la habitación', '2025-05-18', 107, 'Abierto'),
('Otros', 'Objeto olvidado por cliente', '2025-05-17', 106, 'Cerrado'),
('Limpieza', 'Necesita cambio de toallas', '2025-05-21', 109, 'Abierto'),
('Mantenimiento', 'Televisor no enciende', '2025-05-22', 110, 'Abierto');
