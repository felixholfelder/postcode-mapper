FROM openjdk:17-alpine

ARG revision
ENV revision="1.0.0"

COPY . .
RUN ./mvnw clean install package -Drevision=${revision}

ARG jarPath="target/postcode-mapper-${revision}.jar"

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "$jarPath"]
