# Imagen base de Java 21
FROM eclipse-temurin:21-jdk

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el JAR compilado desde el host al contenedor
COPY build/libs/TribeMe_Backend-0.0.1-SNAPSHOT.jar app.jar

# Crea el directorio donde se almacenarán las imágenes y da permisos de lectura/escritura
RUN mkdir -p /app/imagenes && chmod -R 777 /app/imagenes

# Expone el puerto que usará Spring Boot
EXPOSE 8081

# Comando para ejecutar el JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
