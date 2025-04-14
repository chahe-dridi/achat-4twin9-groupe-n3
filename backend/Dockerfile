FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/achat-backend-chaherdridi-4twin9-1.0.jar achat-backend.jar
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "achat-backend.jar"]