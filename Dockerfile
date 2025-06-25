FROM openjdk:21-jdk
CMD ["./mvn", "clean", "package"]
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]