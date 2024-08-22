FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/game-service-0.0.1-SNAPSHOT.jar /app/game-service.jar
ENTRYPOINT ["java", "-jar", "/app/game-service.jar"]
