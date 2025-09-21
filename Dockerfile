FROM maven:3.9.9-eclipse-temurin-21

WORKDIR /app

# JAR dosyasını kopyala (CI/CD'den gelecek)
COPY jaunts-1.0.0.jar /app/app.jar

# Curl ekle (healthcheck için)
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Non-root user oluştur
RUN useradd -r -s /bin/false appuser
RUN chown appuser:appuser /app/app.jar
USER appuser

# Uygulamanın çalıştığı portu expose et
EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

CMD ["java", "-Xmx512m", "-Xms256m", "-jar", "/app/app.jar"]
