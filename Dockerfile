# --- Stage 1: Build the Application ---
# Use the newer Maven image based on Eclipse Temurin
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# --- Stage 2: Run the Application ---
# Switch from 'openjdk' to 'eclipse-temurin'
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]