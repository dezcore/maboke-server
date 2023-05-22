# syntax=docker/dockerfile:1
FROM eclipse-temurin:17-jdk-jammy as base

WORKDIR /opt/app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY ./src ./src
RUN ./mvnw clean install

#WORKDIR /app
#COPY .mvn/ .mvn
#COPY mvnw pom.xml ./
#RUN ./mvnw dependency:resolve
#COPY src ./src

FROM base as test
RUN ["./mvnw", "test"]

FROM base as dev
CMD ["./mvnw", "spring-boot:run"]

#FROM base as test_dev
#CMD ["./mvnw", "spring-boot:run", "-Dspring-boot.run.profiles=dev"]

FROM eclipse-temurin:17-jre-jammy
WORKDIR /opt/app
EXPOSE 8080
COPY --from=builder /opt/app/target/*.jar /opt/app/*.jar
ENTRYPOINT ["java", "-jar", "/opt/app/*.jar" ]