FROM eclipse-temurin:21-jdk

#ubicacion del archivo ejecutable
COPY build/libs/TribeMe_Backend-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
