FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/*.jar authentication-0.0.1-SNAPSHOT.jar
EXPOSE 7070
ENTRYPOINT ["java", "-jar", "authentication-0.0.1-SNAPSHOT.jar"]