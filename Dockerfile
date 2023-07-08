FROM openjdk:17-alpine

ARG version
ENV version="1.0.0"

COPY . .
RUN ./mvnw clean install package -Dversion

EXPOSE 8080
COPY target/postcode-mapper-*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
