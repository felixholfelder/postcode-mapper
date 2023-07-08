FROM openjdk:17-alpine

ARG version
ENV version=$version

COPY . .
RUN ./mvnw clean install package -Dversion=$version

EXPOSE 8080
COPY target/postcode-mapper-${version}.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
