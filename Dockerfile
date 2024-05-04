FROM openjdk:17
ARG JAR_FILE=siRelawan/build/libs/siRelawan-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 9099
ENTRYPOINT ["java","-jar","/app.jar"]