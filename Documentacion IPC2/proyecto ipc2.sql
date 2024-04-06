DROP DATABASE IF EXISTS sistema_paquetes;

CREATE DATABASE sistema_paquetes;
USE sistema_paquetes;

CREATE TABLE cliente(
nit INT NOT NULL,
nombre VARCHAR(30) NOT NULL,
direccion VARCHAR(30) NOT NULL,
telefono VARCHAR(10) NOT NULL,
PRIMARY KEY (nit)
);

CREATE TABLE usuarios(
id_usuario INT AUTO_INCREMENT NOT NULL,
nombre VARCHAR(30) NOT NULL,
rol INT NOT NULL,
contrasena VARCHAR(20) NOT NULL,
estado BOOLEAN NOT NULL,
nombre_usuario VARCHAR(30) NOT NULL,
precio_Xlibra DOUBLE,
PRIMARY KEY (id_usuario)

);

CREATE TABLE punto_control(
id INT AUTO_INCREMENT NOT  NULL,
tarifa INT NOT NULL,
id_usuario INT NOT NULL,
limite_cola INT NOT NULL,
PRIMARY KEY(id),
CONSTRAINT id_usuario_operador_fk
FOREIGN KEY (id_usuario)
REFERENCES usuarios(id_usuario)
ON DELETE CASCADE
ON UPDATE CASCADE
);

CREATE TABLE ruta (
id_ruta INT AUTO_INCREMENT NOT NULL ,
disponible BOOLEAN NOT NULL,
estado BOOLEAN NOT NULL,
id_usuario INT NOT NULL,
id_punto_control INT NOT NULL,
limite_cola INT NOT NULL, 
PRIMARY KEY (id_ruta),
CONSTRAINT id_usuario_fk
FOREIGN KEY (id_usuario)
REFERENCES usuarios(id_usuario)
ON DELETE CASCADE
ON UPDATE CASCADE,
CONSTRAINT id_punto_control_fk
FOREIGN KEY(id_punto_control)
REFERENCES punto_control(id)
ON DELETE CASCADE
ON UPDATE CASCADE
);

CREATE TABLE paquete(
codigo INT AUTO_INCREMENT NOT NULL,
detalle VARCHAR(1000) NOT NULL,
estado INT NOT NULL,
cuota DOUBLE NOT NULL,
destino VARCHAR(30) NOT NULL,
id_ruta INT NOT NULL,
id_punto_control INT NOT NULL,
id_usuario_creador INT NOT NULL,
hora_ingreso DATETIME,
hora_salida DATETIME,
peso DOUBLE NOT NULL,
PRIMARY KEY(codigo),
CONSTRAINT id_ruta_fk
FOREIGN KEY (id_ruta)
REFERENCES ruta(id_ruta)
ON DELETE CASCADE
ON UPDATE CASCADE,
CONSTRAINT id_punto_control_fkp
FOREIGN KEY(id_punto_control)
REFERENCES punto_control(id)
ON DELETE CASCADE
ON UPDATE CASCADE,
CONSTRAINT id_usuario_creador_fk
FOREIGN KEY (id_usuario_creador)
REFERENCES usuarios(id_usuario)
ON DELETE CASCADE
ON UPDATE CASCADE
);



CREATE TABLE cliente_paquete(
nit_cliente INT NOT NULL,
codigo_paquete INT NOT NULL,
PRIMARY KEY(nit_cliente, codigo_paquete),
CONSTRAINT nit_cliente_fk
FOREIGN KEY(nit_cliente)
REFERENCES cliente(nit)
ON DELETE CASCADE
ON UPDATE CASCADE,
CONSTRAINT codigo_paquete_fk
FOREIGN KEY(codigo_paquete)
REFERENCES paquete(codigo)
ON DELETE CASCADE
ON UPDATE CASCADE

);

/*usuario por defecto del sistema contraseña: 1000, admin*/ 
insert into usuarios(id_usuario, nombre, rol, contrasena, estado, nombre_usuario, precio_Xlibra) values(1000, 'admin', 1, '123',1,'admin1', 7);
	
/*aqui empieza el mapeo físico de la creación del nuevo usuario*/
CREATE USER 'proyecto1IPC2_2024'@'localhost' IDENTIFIED BY 'kenny123';
GRANT SELECT ON sistema_paquetes.* TO 'proyecto1IPC2_2024'@'localhost';
GRANT DELETE ON sistema_paquetes.* TO 'proyecto1IPC2_2024'@'localhost';
GRANT UPDATE ON sistema_paquetes.* TO 'proyecto1IPC2_2024'@'localhost';
GRANT INSERT ON sistema_paquetes.* TO 'proyecto1IPC2_2024'@'localhost';
/* EL PUERTO TIENE QUE SER: 3306*/

 USE sistema_paquetes;




