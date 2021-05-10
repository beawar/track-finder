FROM openjdk:15-jdk-slim
EXPOSE '8080'
ARG JAR_FILE=build/libs/*.jar
ADD ${JAR_FILE} track.jar
ENTRYPOINT ["java","-jar","/track.jar"]