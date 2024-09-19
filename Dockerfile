# Use the official Eclipse Temurin image for Java 21 (base image for JDK 21)
# Step 1: Build stage
FROM eclipse-temurin:21-jdk-alpine AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven/Gradle build files to cache dependencies
COPY mvnw pom.xml ./
COPY .mvn .mvn

# Install the necessary dependencies (using Maven)
RUN ./mvnw dependency:go-offline -B

# Copy the entire project to the container
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Step 2: Run stage (for production use)
FROM eclipse-temurin:21-jre-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=builder /app/target/*.jar app.jar

# Expose port 8080 (or the port your Spring Boot app uses)
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
