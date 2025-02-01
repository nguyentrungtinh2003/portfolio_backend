FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/portfolio_backend-0.0.1-SNAPSHOT.jar /app/portfolio_backend-0.0.1-SNAPSHOT.jar

# Expose the port the app runs on
EXPOSE 8081

# Command to run the application
ENTRYPOINT ["java", "-jar", "portfolio_backend-0.0.1-SNAPSHOT.jar"]