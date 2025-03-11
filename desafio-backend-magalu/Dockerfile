
FROM eclipse-temurin:17-jdk as builder

WORKDIR /app

COPY . .

RUN apt-get update && apt-get install -y --no-install-recommends apt-utils
RUN apt-get install -y maven
RUN mvn clean install

FROM eclipse-temurin:17-jdk

WORKDIR /app

RUN apt-get update && apt-get install -y iputils-ping telnet

COPY --from=builder /app/INFRASTRUCTURE/target/infrastructure-3.4.2.jar .
COPY --from=builder /app/INFRASTRUCTURE/src/main/resources/application.properties .

EXPOSE 9092

CMD ["java", "-jar", "./infrastructure-3.4.2.jar", "--spring.config.location=./application.properties"]
