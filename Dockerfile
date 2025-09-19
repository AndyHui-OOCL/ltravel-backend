# Use OpenJDK 20 as base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy gradle files
COPY gradle gradle
COPY build.gradle settings.gradle gradlew ./
RUN chmod +x gradlew

# Copy source code
COPY src src

# Build the application
RUN ./gradlew build -x test --no-daemon

# Copy the built jar
RUN JAR_FILE=$(ls build/libs/*.jar | grep -v plain | head -n 1) && cp ${JAR_FILE} app.jar

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]