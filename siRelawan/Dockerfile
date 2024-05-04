# FROM gradle:jdk17-alpine
# ARG PRODUCTION
# ARG JDBC_DATABASE_URL
# ARG JDBC_DATABASE_PASSWORD
# ARG JDBC_DATABASE_USERNAME

# ENV PRODUCTION ${PRODUCTION}
# ENV JDBC_DATABASE_PASSWORD ${JDBC_DATABASE_PASSWORD}
# ENV JDBC_DATABASE_URL ${JDBC_DATABASE_URL}
# ENV JDBC_DATABASE_USERNAME ${JDBC_DATABASE_USERNAME}

# WORKDIR /app
# COPY ./build/libs/siRelawan-0.0.1-SNAPSHOT.jar ./app
# EXPOSE 8080
# CMD ["java","-jar","siRelawan-0.0.1-SNAPSHOT.jar"]

# FROM openjdk:17
# ARG JAR_FILE=build/libs/siRelawan-0.0.1-SNAPSHOT.jar
# COPY ${JAR_FILE} app.jar
# EXPOSE 15001
# ENTRYPOINT ["java","-jar","/app.jar"]

#
# Build stage
#
FROM gradle:jdk17 AS build
COPY . .
RUN ./gradlew build

#
# Package stage
#
FROM openjdk:17
COPY --from=build /home/gradle/build/libs/siRelawan-0.0.1-SNAPSHOT.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.jar"]
