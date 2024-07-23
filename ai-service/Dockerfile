FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/ai-service-0.0.1-SNAPSHOT.jar FlashcardwebAiService.jar
ENTRYPOINT ["java","-jar","FlashcardwebAiService.jar"]