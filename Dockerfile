FROM gradle:jdk16 as build
COPY --chown=gradle:gradle . /home/gradle
WORKDIR /home/gradle
RUN gradle build

FROM openjdk:16-alpine AS deployment
RUN mkdir /app
COPY --from=build /home/gradle/build/libs/*.jar /app/springboot-app.jar
ENTRYPOINT exec java $JAVA_OPTS -jar /app/springboot-app.jar