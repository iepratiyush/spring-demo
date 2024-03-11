# Stage 1: Build Step
FROM maven:3.8.4-openjdk-11-slim AS build
WORKDIR /app

# Copy only the POM file to cache dependency
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the application source code and build
COPY src src
RUN mvn clean package -DskipTests

# Stage 2: Final Steps
FROM openjdk:11.0.12-jre-slim
WORKDIR /app

# Create a non-root user for running the application
RUN adduser --disabled-password --gecos '' springuser
USER springuser

# Copy the WAR file from build stage
ARG VERSION=0.0.1
COPY --from=build /app/target/demo-${VERSION}-SNAPSHOT.war app.war

# Expose the application port
EXPOSE 8080

# Running command, specify resource constraints
CMD ["java", "-Xmx256m", "-jar", "app.war"]