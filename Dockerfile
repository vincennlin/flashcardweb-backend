FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/flashcardweb-backend-0.3.1-SNAPSHOT.jar FlashcardwebBackend.jar
ENTRYPOINT ["java","-jar","FlashcardwebBackend.jar"]