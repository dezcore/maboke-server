# syntax=docker/dockerfile:1
FROM eclipse-temurin:17-jdk-jammy as base

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve

COPY src ./src

FROM base as test
RUN ["./mvnw", "test"]

FROM base as dev
CMD ["./mvnw", "spring-boot:run"]

FROM base as test_dev
CMD ["./mvnw", "spring-boot:run", "-Dspring-boot.run.profiles=dev"]

FROM base as build
RUN ./mvnw package -P prod
