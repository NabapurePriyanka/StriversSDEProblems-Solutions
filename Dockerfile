# Use an OpenJDK base image
FROM adoptopenjdk:11-jdk-hotspot

# Install Redis CLI
RUN apt-get update && apt-get install -y redis-tools

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled JAR file from the target directory to the container
COPY target/*.jar app.jar

# Expose the port on which your Spring Boot application listens
EXPOSE 8080

# Run the JAR file when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]
