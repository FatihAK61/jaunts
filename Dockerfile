# Multi-stage build ile optimize edilmiş Dockerfile
FROM eclipse-temurin:21-jre-alpine AS runtime

# Curl ekle (daha küçük alpine paketi)
RUN apk add --no-cache curl

# Non-root user oluştur
RUN addgroup -g 1001 -S appuser && adduser -u 1001 -S appuser -G appuser

WORKDIR /app

# JAR dosyasını kopyala
COPY jaunts-1.0.0.jar app.jar

# Ownership ayarla
RUN chown appuser:appuser app.jar

USER appuser

EXPOSE 8080

# JVM optimizasyonları
ENV JAVA_OPTS="-XX:+UseG1GC -XX:MaxRAMPercentage=75 -XX:+UseStringDeduplication"

HEALTHCHECK --interval=15s --timeout=5s --start-period=30s --retries=2 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

CMD ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
