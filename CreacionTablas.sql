-- Eliminar triggers
DROP TRIGGER trg_roles;
DROP TRIGGER trg_usuarios;
DROP TRIGGER trg_categorias;
DROP TRIGGER trg_publicaciones;
DROP TRIGGER trg_comentarios;

-- Eliminar secuencias
DROP SEQUENCE seq_roles;
DROP SEQUENCE seq_usuarios;
DROP SEQUENCE seq_categorias;
DROP SEQUENCE seq_publicaciones;
DROP SEQUENCE seq_comentarios;

-- Eliminar tablas 
DROP TABLE comentarios CASCADE CONSTRAINTS;
DROP TABLE publicaciones CASCADE CONSTRAINTS;
DROP TABLE usuarios CASCADE CONSTRAINTS;
DROP TABLE categorias CASCADE CONSTRAINTS;
DROP TABLE roles CASCADE CONSTRAINTS;


-- Crear tablas
CREATE TABLE roles (
    id_rol NUMBER PRIMARY KEY,
    nombre_rol VARCHAR2(50) NOT NULL
);

CREATE TABLE usuarios (
    id_usuario NUMBER PRIMARY KEY,
    nombre_usuario VARCHAR2(50) NOT NULL,
    email VARCHAR2(100) NOT NULL UNIQUE,
    contraseña VARCHAR2(100) NOT NULL,
    rol_id NUMBER,
    FOREIGN KEY (rol_id) REFERENCES roles(id_rol)
);

CREATE TABLE categorias (
    id_categoria NUMBER PRIMARY KEY,
    nombre_categoria VARCHAR2(100) NOT NULL
);

CREATE TABLE publicaciones (
    id_publicacion NUMBER PRIMARY KEY,
    titulo VARCHAR2(200) NOT NULL,
    contenido CLOB NOT NULL,
    fecha_publicacion DATE DEFAULT SYSDATE,
    id_usuario NUMBER,
    id_categoria NUMBER,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_categoria) REFERENCES categorias(id_categoria)
);

CREATE TABLE comentarios (
    id_comentario NUMBER PRIMARY KEY,
    contenido CLOB NOT NULL,
    fecha_comentario DATE DEFAULT SYSDATE,
    id_usuario NUMBER,
    id_publicacion NUMBER,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_publicacion) REFERENCES publicaciones(id_publicacion)
);

-- Crear secuencias para IDs
CREATE SEQUENCE seq_roles START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_usuarios START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_categorias START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_publicaciones START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_comentarios START WITH 1 INCREMENT BY 1;

-- Crear triggers para autoincrementar IDs
CREATE OR REPLACE TRIGGER trg_roles BEFORE INSERT ON roles
FOR EACH ROW
BEGIN
    SELECT seq_roles.NEXTVAL INTO :NEW.id_rol FROM dual;
END;
/

CREATE OR REPLACE TRIGGER trg_usuarios BEFORE INSERT ON usuarios
FOR EACH ROW
BEGIN
    SELECT seq_usuarios.NEXTVAL INTO :NEW.id_usuario FROM dual;
END;
/

CREATE OR REPLACE TRIGGER trg_categorias BEFORE INSERT ON categorias
FOR EACH ROW
BEGIN
    SELECT seq_categorias.NEXTVAL INTO :NEW.id_categoria FROM dual;
END;
/

CREATE OR REPLACE TRIGGER trg_publicaciones BEFORE INSERT ON publicaciones
FOR EACH ROW
BEGIN
    SELECT seq_publicaciones.NEXTVAL INTO :NEW.id_publicacion FROM dual;
END;
/

CREATE OR REPLACE TRIGGER trg_comentarios BEFORE INSERT ON comentarios
FOR EACH ROW
BEGIN
    SELECT seq_comentarios.NEXTVAL INTO :NEW.id_comentario FROM dual;
END;
/

-- Insertar roles
INSERT INTO roles (nombre_rol) VALUES ('Jugador');
INSERT INTO roles (nombre_rol) VALUES ('Maestro del foro');
INSERT INTO roles (nombre_rol) VALUES ('Game Master');

-- Insertar usuarios
INSERT INTO usuarios (nombre_usuario, email, contraseña, rol_id) VALUES ('gamer1', 'gamer1@example.com', 'pass123', 1);
INSERT INTO usuarios (nombre_usuario, email, contraseña, rol_id) VALUES ('moderador1', 'moderador1@example.com', 'pass456', 2);
INSERT INTO usuarios (nombre_usuario, email, contraseña, rol_id) VALUES ('admin1', 'admin1@example.com', 'adminpass', 3);

-- Insertar categorías
INSERT INTO categorias (nombre_categoria) VALUES ('Noticias y lanzamientos');
INSERT INTO categorias (nombre_categoria) VALUES ('Juegos de PC');
INSERT INTO categorias (nombre_categoria) VALUES ('Juegos de consola');
INSERT INTO categorias (nombre_categoria) VALUES ('Juegos indie y móviles');
INSERT INTO categorias (nombre_categoria) VALUES ('Recomendaciones y reseñas');

-- Insertar publicaciones
INSERT INTO publicaciones (titulo, contenido, id_usuario, id_categoria) VALUES ('Lanzamiento de GTA VI', 'Se ha anunciado la fecha oficial de lanzamiento.', 1, 1);
INSERT INTO publicaciones (titulo, contenido, id_usuario, id_categoria) VALUES ('Los mejores juegos para PC 2024', 'Aquí tienes una lista de los juegos más esperados.', 1, 2);
INSERT INTO publicaciones (titulo, contenido, id_usuario, id_categoria) VALUES ('Nintendo Switch 2 rumores', 'Todo lo que sabemos hasta ahora.', 2, 3);

-- Insertar comentarios
INSERT INTO comentarios (contenido, id_usuario, id_publicacion) VALUES ('¡Excelente noticia!', 2, 1);
INSERT INTO comentarios (contenido, id_usuario, id_publicacion) VALUES ('Gran lista, ¡gracias!', 3, 2);
INSERT INTO comentarios (contenido, id_usuario, id_publicacion) VALUES ('Ojalá lo confirmen pronto.', 1, 3);

COMMIT;