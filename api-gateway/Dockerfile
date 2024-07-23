FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/api-gateway-0.0.1-SNAPSHOT.jar FlashcardwebApiGateway.jar
ENTRYPOINT ["java","-jar","FlashcardwebApiGateway.jar"]