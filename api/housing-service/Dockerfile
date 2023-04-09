FROM maven:3.8.3-jdk-11-slim
WORKDIR /app
COPY pom.xml .
# Resolve everything this project is dependent on
RUN mvn dependency:go-offline
COPY src/ ./src/
# Skip database localhost test
RUN mvn clean package -Dmaven.test.skip=true

FROM openjdk:11-jre-slim
# Copy just the built maven package from the previous stage into this new stage. 
COPY --from=0 /app/target/housing-service-0.0.1-SNAPSHOT.jar /app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/app.jar"]
