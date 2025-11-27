# Multi-stage build para optimizar el tamaño y velocidad
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copiar solo los archivos de configuración de Maven primero (para cache de dependencias)
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Descargar dependencias (esto se cachea si pom.xml no cambia)
RUN mvn dependency:go-offline -B

# Copiar el código fuente
COPY src ./src

# Compilar y empaquetar la aplicación
RUN mvn clean package -DskipTests -B

# Stage final: imagen de runtime más pequeña
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copiar el JAR desde el stage de build
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto (Railway lo configurará automáticamente)
EXPOSE 8080

# Variables de entorno
ENV PORT=8080

# Ejecutar la aplicación
ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT:-8080} -jar app.jar"]

