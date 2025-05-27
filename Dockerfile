FROM eclipse-temurin:21-jdk

#ubicacion del archivo ejecutable
COPY build/libs/TribeMe_Backend-0.0.1-SNAPSHOT.jar app.jar

# Crear el directorio y darle permisos de acceso público
RUN mkdir -p /app/TribeMe/tribus/imagenes && chmod -R 777 /app/TribeMe/tribus/imagenes

ENTRYPOINT ["java", "-jar", "/app.jar"]
