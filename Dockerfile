FROM eclipse-temurin:23-jdk
LABEL authors="kirito"
WORKDIR /app
COPY target/ComputerClub-0.0.1-SNAPSHOT.jar app.jar


ENTRYPOINT ["java", "-jar", "app.jar"]