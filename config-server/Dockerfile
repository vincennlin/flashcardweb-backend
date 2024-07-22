FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY flashcardwebApiEncryptionKey.jks flashcardwebApiEncryptionKey.jks
COPY target/config-server-0.0.1-SNAPSHOT.jar FlashcardwebConfigServer.jar
ENTRYPOINT ["java","-jar","FlashcardwebConfigServer.jar"]