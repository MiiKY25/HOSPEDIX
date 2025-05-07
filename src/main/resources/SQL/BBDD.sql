CREATE TABLE empleados (
    dni VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(50),
    apellido VARCHAR(50),
    telefono VARCHAR(20),
    direccion VARCHAR(200),
    cargo ENUM('recepcionista', 'gerente', 'limpieza', 'mantenimiento', 'hostelería', 'otros'),
    horario_trabajo VARCHAR(100)
);

CREATE TABLE habitaciones (
    num_habitacion INT PRIMARY KEY,
    estado ENUM('disponible', 'ocupada', 'mantenimiento'),
    tipo ENUM('individual', 'doble', 'suite'),
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
    fecha_checkin DATE,
    fecha_checkout DATE,
    estado_pago ENUM('pagado', 'pendiente', 'cancelado'),
    cantidad_personas INT,
    precio INT,
    extras INT,
    FOREIGN KEY (num_habitacion) REFERENCES habitaciones(num_habitacion)
);

CREATE TABLE incidencias (
    id_incidencia INT AUTO_INCREMENT PRIMARY KEY,
    tipo_incidencia ENUM('limpieza', 'mantenimiento', 'otros'),
    descripcion VARCHAR(100),
    fecha_reporte DATE,
    num_habitacion INT,
    estado ENUM('abierto', 'cerrado'),
    FOREIGN KEY (num_habitacion) REFERENCES habitaciones(num_habitacion)
);
