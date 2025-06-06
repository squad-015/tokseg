FROM maven:3.8.4-openjdk-17 as build-image
RUN mkdir -p /app/source
COPY . /app/source
WORKDIR /app/source
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
COPY --from=build-image /app/source/target/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]