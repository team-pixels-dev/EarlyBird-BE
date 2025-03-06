FROM openjdk:21
ARG JAR_FILE=build/libs/earlybird-server.jar
COPY ${JAR_FILE} app.jar
RUN sudo ln -snf /usr/share/zoneinfo/Asia/Seoul /etc/localtime
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]