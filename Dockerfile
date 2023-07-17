FROM registry.access.redhat.com/ubi8/openjdk-17:1.14

ARG revision
ENV revision="1.0.0"

COPY . .
RUN ./mvnw clean install package -Drevision=${revision}

ARG jarPath="target/postcode-mapper-${revision}.jar"

RUN echo $jarPath

EXPOSE 8080

ENTRYPOINT ["java", "-Dserver.port=$PORT $JAVA_OPTS", "-jar", "target/postcode-mapper-1.0.0.jar"]
