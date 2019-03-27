FROM opernjdk:11-jdk-alpine
VOLUME /tmp
COPY target/*jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]