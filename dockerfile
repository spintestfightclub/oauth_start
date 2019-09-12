FROM maven:3.6.1-jdk-11 as builder
WORKDIR /usr/src/app
COPY src /usr/src/app/src
COPY img /usr/src/app/img
COPY pom.xml /usr/src/app/
RUN mvn package

# Final image
FROM maven:3.6.1-jdk-11-slim
EXPOSE 8080
COPY --from=builder /usr/src/app/target/spring-oauth2-resource-server-demo-0.0.1-SNAPSHOT.jar /usr/src/app/spring-oauth2-resource-server-demo-0.0.1-SNAPSHOT.jar
ENTRYPOINT java -jar /usr/src/app/spring-oauth2-resource-server-demo-0.0.1-SNAPSHOT.jar
