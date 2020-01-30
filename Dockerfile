FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8080
COPY application/build/libs/app_be-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-jar","/app_be.jar"]