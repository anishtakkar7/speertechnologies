FROM openjdk:17-jdk-slim AS build

COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR NotesApplication
COPY --from=build target/*.jar NotesApplication.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=prod" ,"-jar", "NotesApplication.jar"]