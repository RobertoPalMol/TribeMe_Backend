FROM amazoncorretto:20.0.2-alpine-jdk

#ubicacion del archivo ejecutable
COPY build/libs/TribeMe_Backend-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT["java" , "-jar" , "/*app.jar"]