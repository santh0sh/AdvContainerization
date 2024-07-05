FROM docker.io/maven:3.6.3-jdk-11 as builder
COPY . .
RUN mvn clean package

FROM registry.access.redhat.com/ubi8/openjdk-11
COPY --from=builder target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
