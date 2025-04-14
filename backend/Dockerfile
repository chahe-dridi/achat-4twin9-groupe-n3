# Use the Maven base image to build the application
FROM maven:3.8.4-openjdk-17-slim as builder

# Set the working directory
WORKDIR /app

# Copy the source code
COPY . /app/

# Run Maven build to package the application (using the image's Maven directly)
RUN mvn clean package -DskipTests

# Use OpenJDK to run the packaged JAR
FROM openjdk:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the packaged JAR from the builder stage
COPY --from=builder /app/target/achat-backend-*.jar achat-backend.jar

# Expose the port the app runs on
EXPOSE 8089

# Command to run the app
ENTRYPOINT ["java", "-jar", "achat-backend.jar"]
