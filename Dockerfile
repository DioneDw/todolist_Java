FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y

# Defina o diretório de trabalho para /app
WORKDIR /app

# Copie o arquivo pom.xml para o diretório de trabalho
COPY src/pom.xml .

RUN apt-get install maven -y
RUN mvn clean install

FROM openjdk:17-jdk-slim

EXPOSE 8090

# Defina o diretório de trabalho para /app
WORKDIR /app

COPY --from=build /app/target/todolist-1.0.0.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]