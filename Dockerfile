# Step 1: Base image with Java 17 runtime
FROM eclipse-temurin:17-jre-jammy

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy the built JAR file into the container
COPY build/libs/*.jar app.jar

# Step 4: Expose the port the application will run on (optional, it's good practice)
EXPOSE 18080

# Step 5: Define the entry point for the container
ENTRYPOINT ["java", "-jar", "app.jar"]