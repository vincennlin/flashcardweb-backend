FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/user-service-1.0.2-SNAPSHOT.jar FlashcardwebUserService.jar
ENTRYPOINT ["java","-jar","FlashcardwebUserService.jar"]