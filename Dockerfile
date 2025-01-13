from openjdk:17-jdk-slim

WORKDIR /app

copy target/*.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]