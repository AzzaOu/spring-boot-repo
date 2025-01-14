FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the specific JAR file into the container
COPY target/demo-0.0.1-SNAPSHOT.jar /app/demo-0.0.1-SNAPSHOT.jar

# Set the entrypoint to execute the copied JAR file img
ENTRYPOINT ["java", "-jar", "/app/demo-0.0.1-SNAPSHOT.jar"]
