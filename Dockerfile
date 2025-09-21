# Build stage
FROM openjdk:23-jre-slim AS build

WORKDIR /app

# Copy Maven/Gradle files
COPY pom.xml .
# Eğer Maven wrapper kullanıyorsanız:
COPY .mvn .mvn
COPY mvnw .

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src ./src

# Build application
RUN ./mvnw clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:23-jre-alpine

WORKDIR /app

# Copy jar file from build stage
COPY --from=build /app/target/*.jar app.jar

# Create non-root user
RUN addgroup --system spring && adduser --system spring --ingroup spring
USER spring:spring

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=30s --retries=3 \
  CMD curl -f http://localhost:1461/actuator/health || exit 1

EXPOSE 1461

ENTRYPOINT ["java", "-jar", "app.jar"]
