# Stage 1: Build
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
COPY configuration/pom.xml configuration/pom.xml
COPY application/pom.xml application/pom.xml
COPY configuration/src configuration/src
COPY application/src application/src

RUN mvn clean package -Pprod -DskipTests

# Stage 2: Runtime
FROM tomcat:10.1-jdk17
WORKDIR /usr/local/tomcat

RUN rm -rf webapps/*

COPY --from=build /app/application/target/raw-spring-app-application.war webapps/room_api.war

ENV SPRING_PROFILES_ACTIVE=prod

EXPOSE 8080
CMD ["catalina.sh", "run"]
