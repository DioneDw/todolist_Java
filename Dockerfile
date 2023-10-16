FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y

COPY src .

RUN apt-get install maven -y
RUN /todolist/mvn clean install

FROM openjdk:17-jdk-slim

EXPOSE 8090

COPY --from=build /target/todolist-1.0.0.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
