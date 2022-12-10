FROM openjdk:17-jdk-alpine AS build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw install


FROM openjdk:17-jdk-alpine
#VOLUME /tmp
ARG JAR_FILE=/workspace/app/target/*.jar
COPY --from=build ${JAR_FILE} web-server.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/web-server.jar"]
