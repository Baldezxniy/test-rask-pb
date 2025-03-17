FROM maven:3.9.9-amazoncorretto-17-alpine AS build
WORKDIR /app

COPY pom.xml /app/pom.xml

COPY common /app/common

RUN mvn install -DskipTests -f /app/common/pom.xml

COPY mock-service-1/pom.xml /app/mock-service-1/pom.xml

RUN mvn install -N -DskipTests -f /app/pom.xml

COPY mock-service-1/src /app/mock-service-1/src

WORKDIR /app/mock-service-1
RUN mvn dependency:go-offline -B -f /app/mock-service-1/pom.xml

RUN mvn clean package -DskipTests -B -Dmaven.test.skip=true -f /app/mock-service-1/pom.xml

FROM openjdk:17-slim AS builder
WORKDIR /app
COPY --from=build /app/mock-service-1/target/*.jar application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM openjdk:17-slim
WORKDIR /app
COPY --from=builder /app/dependencies/ ./
COPY --from=builder /app/spring-boot-loader/ ./
COPY --from=builder /app/snapshot-dependencies/ ./
COPY --from=builder /app/application/ ./
EXPOSE 8080
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
