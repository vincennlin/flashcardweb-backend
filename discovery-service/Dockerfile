FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/discovery-service-0.0.1-SNAPSHOT.jar FlashcardwebDiscoveryService.jar
ENTRYPOINT ["java","-jar","FlashcardwebDiscoveryService.jar"]