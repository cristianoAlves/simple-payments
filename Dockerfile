# Stage 1: Build
FROM maven:3.9.4-eclipse-temurin-17-alpine AS builder

# Create app directory and copy everything
WORKDIR /app
COPY . .

# Build the JAR from the root
RUN ./mvnw clean package -DskipTests

# Stage 2: Copy the JAR and run it
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=builder /app/target/payments-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]