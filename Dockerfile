FROM maven:3.8.5-openjdk-17-slim as buildApp
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-alpine
COPY --from=buildApp "target/*.jar" "spring-crud-api-rest.jar"
EXPOSE 8080

ENTRYPOINT ["java","-jar","spring-crud-api-rest.jar"]