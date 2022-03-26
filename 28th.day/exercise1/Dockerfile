FROM openjdk:17-oracle
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} lottery-app.jar
EXPOSE 8800
ENTRYPOINT ["java","-jar","/lottery-app.jar"]