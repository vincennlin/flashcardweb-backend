FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/note-service-1.0.2-SNAPSHOT.jar FlashcardwebNoteService.jar
ENTRYPOINT ["java","-jar","FlashcardwebNoteService.jar"]