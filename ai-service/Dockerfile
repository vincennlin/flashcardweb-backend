FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/ai-service-1.0.2-SNAPSHOT.jar FlashcardwebAiService.jar
ENTRYPOINT ["java","-jar","FlashcardwebAiService.jar"]