FROM openjdk:17-alpine

COPY "target/*.jar" "spring-crud-api-rest.jar"
ENTRYPOINT ["java","-jar","spring-crud-api-rest.jar"]

EXPOSE 8080