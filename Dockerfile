FROM openjdk:17-alpine

ARG revision
ENV revision="1.0.0"

COPY . .
RUN ./mvnw clean install package -Drevision=${revision}

EXPOSE 8080
COPY target/postcode-mapper-${revision}.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
