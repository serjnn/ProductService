FROM openjdk:17-jdk-slim as builder

WORKDIR /app
COPY /target/ProductService-0.0.1-SNAPSHOT.jar /app/ProductService.jar

EXPOSE 7002

ENTRYPOINT ["java", "-jar", "/app/ProductService.jar"]