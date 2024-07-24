FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/flashcard-service-1.0.2-SNAPSHOT.jar FlashcardwebFlashcardService.jar
ENTRYPOINT ["java","-jar","FlashcardwebFlashcardService.jar"]