FROM openjdk:17-alpine

ARG revision
ENV revision="1.0.0"

COPY . .
RUN ./mvnw clean install package -Drevision=${revision}

RUN ls -l
RUN ls -l target

COPY target/postcode-mapper-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
