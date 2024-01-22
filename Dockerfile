FROM openjdk:17
ENV TZ=Asia/Seoul

RUN ln -snf /usr/share/zoneinfo/Asia/Seoul /etc/localtime
RUN echo Asia/Seoul > /etc/timezone

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "app.jar"]