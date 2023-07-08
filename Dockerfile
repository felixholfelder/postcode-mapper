FROM openjdk:17-alpine

ARG version
ENV version=$version

COPY . .
RUN ./mvnw clean install package

EXPOSE 8080
COPY target/postcode-mapper-*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
