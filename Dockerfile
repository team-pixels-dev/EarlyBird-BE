FROM openjdk:21
ARG JAR_FILE=build/libs/earlybird-server.jar
COPY ${JAR_FILE} app.jar
RUN mkdir -p /fluent-bit/config /tmp/fluent-bit/s3
COPY fluent-bit/fluent-bit.conf /fluent-bit/config/
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]