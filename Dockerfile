FROM openjdk:21
ARG JAR_FILE=build/libs/earlybird-server.jar
COPY ${JAR_FILE} app.jar
RUN apt-get update && apt-get install -y tzdata \
    && ln -snf /usr/share/zoneinfo/Asia/Seoul /etc/localtime \
    && echo "Asia/Seoul" > /etc/timezone \
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]