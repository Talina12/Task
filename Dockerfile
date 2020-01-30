FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/app_be-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app_be.jar
ENTRYPOINT ["java","-jar","/app_be.jar"]