# Étape 1 : build de l'application
FROM maven:3.9.5-eclipse-temurin-17 as builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Étape 2 : image finale
FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
