FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/email-service-0.0.1-SNAPSHOT.jar mail-service.jar
ENTRYPOINT ["java","-jar","/mail-service.jar"]
