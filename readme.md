# ✨ Microservicio: Control de Usuarios ✨

## 📜 Descripción

**Control de Usuarios** Este microservicio backend forma parte del proyecto Sistema de Foros en Línea y está enfocado en la gestión de usuarios, ha sido desarrollado con Spring Boot, conectado a Oracle Cloud Autonomous Database, y probado con Postman. Permite:

- ✅ Crear, listar, modificar y eliminar usuarios.
- ✅ Validar inicio de sesión (login).
- ✅ Consultar diferentes endpoints.

---

##  📁 Estructura del proyecto

  DemoUsuarios/
  ├── src/main/java/demo/demo/
  │   ├── controller/          # Controladores REST
  │   ├── model/               # Modelos de entidad JPA
  │   ├── repository/          # Repositorios JPA
  │   └── service/             # Lógica de negocio
  ├── src/main/resources/
  │   └── application.properties
  ├── src/
  │   └──test
  ├── pom.xml
  └── README.md

---

##  🚀 Endpoints disponibles

- 🔎 Obtener todos los usuarios

  GET /usuarios📥 Respuesta: Lista de usuarios.

- 🔎 Obtener un usuario por ID

  GET /usuarios/{id}📥 Respuesta: Usuario encontrado o vacío si no existe.

- ➕ Crear un usuario

  POST /usuarios📤 Body JSON:

  {
    "nombreUsuario": "nuevoGamer",
    "email": "nuevo@example.com",
    "contraseña": "clave1234",
    "rolId": 1
  }

- ✏️ Actualizar un usuario

  PUT /usuarios/{id}📤 Body JSON:

  {
    "nombreUsuario": "usuarioActualizado",
    "email": "actualizado@example.com",
    "contraseña": "nuevaClave456",
    "rolId": 2
  }

- ❌ Eliminar un usuario

  DELETE /usuarios/{id}

- 🔐 Login de usuario

  POST /usuarios/login📤 Parámetros (en Postman):

  key: email, value: email@example.com

  key: contraseña, value: clave📥

  Respuesta: Mensaje de bienvenida o credenciales incorrectas.

---

## 🛠️ Tecnologías Utilizadas

- ☕ Java 21
- 🌱 Spring Boot 3.x
- 🔎 Spring Data JPA
- 🟠 Oracle JDBC Driver
- 🟠 Oracle Cloud
- 🛠️ Lombok
- 📬 Postman para pruebas


---

## 🗄️ Base de datos

### 📂 Oracle Cloud Autonomous Database

- Tablas utilizadas:

  usuarios

  roles (relacionado con usuarios)

  Configuración en application.properties.

---

## 📥 Instalación y Configuración

### 🛠 **Pasos para ejecutar la app**
1. **Clona este repositorio** en tu máquina local:
  - git clone https://github.com/cratulas/MicroservicioUsuarios
  - Abrir app con tu IDE preferido
  - Ejecutar la APP
  - Probar ENDPOINTS con POSTMAN

2. Usuarios predefinidos para el inicio de sesión
  -  email: gamer1@example.com, password: "pass123"
  -  email: moderador1@example.com, password: "pass456"
  -  email: admin1@example.com, password: "adminpass"

---

## 👨‍💻 Autor

- Mauricio Mora
- Proyecto FullStack 3 — Sistema de Foros en Línea.



