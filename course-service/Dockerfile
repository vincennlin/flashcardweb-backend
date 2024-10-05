FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/course-service-0.0.1-SNAPSHOT.jar FlashcardwebCourseService.jar
ENTRYPOINT ["java","-jar","FlashcardwebCourseService.jar"]