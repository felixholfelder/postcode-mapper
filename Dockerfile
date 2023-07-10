FROM openjdk:17-alpine

VOLUME /tmp

ARG revision
ENV revision="1.0.0"

COPY . .
RUN ./mvnw clean install package -Drevision=${revision}

RUN echo $jarPath

COPY BOOT-INF/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
