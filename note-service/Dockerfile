FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/note-service-0.0.1-SNAPSHOT.jar FlashcardwebNoteService.jar
ENTRYPOINT ["java","-jar","FlashcardwebNoteService.jar"]