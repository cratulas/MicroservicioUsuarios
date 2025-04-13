# Usa OpenJDK como base
FROM openjdk:21-ea-24-oracle

# Crea carpeta de trabajo
WORKDIR /app

# Copia el JAR generado (ajusta el nombre real de tu JAR)
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# Copia el wallet al contenedor
COPY src/main/resources/Wallet_BDFullStack3 /app/oracle_wallet

# Expón el puerto del microservicio
EXPOSE 8081

# Ejecuta la app con la ruta correcta del wallet dentro del contenedor
CMD ["java", "-Dspring.profiles.active=docker", "-jar", "app.jar"]

