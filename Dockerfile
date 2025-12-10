# --- Stage 1: Build the Application ---
# We use a Maven image to compile your Java code into a JAR file
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
# This command builds the app and skips running tests to save time during deployment
RUN mvn clean package -DskipTests

# --- Stage 2: Run the Application ---
# We use a lightweight Java image to actually run the app
FROM openjdk:17-jdk-slim
WORKDIR /app
# We copy the JAR file we built in Stage 1 over to Stage 2
COPY --from=build /app/target/*.jar app.jar

# Tell Render that the app runs on port 8080
EXPOSE 8080

# The command to start the server
ENTRYPOINT ["java", "-jar", "app.jar"]