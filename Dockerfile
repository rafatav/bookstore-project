FROM eclipse-temurin:21-jdk-jammy

RUN groupadd -r spring && useradd -r -g spring spring

WORKDIR /app

COPY target/bookstore-0.0.1-SNAPSHOT.jar app.jar

RUN chown spring:spring app.jar

USER spring:spring

ENTRYPOINT ["java","-jar","app.jar"]
