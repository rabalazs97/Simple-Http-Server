# Stage 1: Build
FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /app

COPY ./src/ .

RUN javac -d bin SimpleHttpServer.java

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=build /app/bin /app

ENTRYPOINT ["java",  "SimpleHttpServer"]