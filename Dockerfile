FROM adoptopenjdk/openjdk11:x86_64-alpine-jdk-11.0.9.1_1-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} adverity.jar
COPY src/main/resources/* ./src/main/resources/
ENTRYPOINT ["java","-jar","/adverity.jar"]