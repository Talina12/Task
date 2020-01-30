FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8080
ADD build/libs/app_be-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-jar","/app_be.jar"]