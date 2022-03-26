FROM openjdk:17-oracle
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} world-app.jar
EXPOSE 8100
ENTRYPOINT ["java","-jar","/world-app.jar"]